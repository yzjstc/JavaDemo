package cn.storm.wcdemo3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import backtype.storm.coordination.BatchOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBatchBolt;
import backtype.storm.transactional.TransactionAttempt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * 此Bolt被设置为Commit阶段的Bolt
 * 所有的批会按照顺序被处理
 * 所以只需要在程序保留最后处理过的批的编号 
 * 后续的批 和这个编号比较 
 * 小于等于这个编号的都是已经处理过的重发数据抛弃
 * 大于这个编号的 是正常数据 正常处理 更新所记录的编号
 */
public class TransWordCountBolt extends BaseBatchBolt <TransactionAttempt>{
	private BatchOutputCollector collector = null;
	private TransactionAttempt id = null;

	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this.collector = collector;
		this.id = id;
	}

	//TODO
	//此处用静态Map作为统计结果的Map 由于采用了filedsGrouping所以可以暂时不考虑并发度高的情况
	//但是处理事务 提交 和 回滚时 比较麻烦
	//真正开发中可以使用数据库来实现数据的保存,应该置于外部的服务器中,而不应在本地worker中
	private static Map<String,Integer> map = new ConcurrentHashMap<>();
	
	private Map<String,Integer> lmap = new HashMap<>();
	@Override
	public void execute(Tuple tuple) {
		//--一个批中的每个tuple都经过此方法，将单词存入本地map
		String word = tuple.getStringByField("word");
		lmap.put(word, lmap.containsKey(word) ? lmap.get(word)+1 : 1);
	}

	//TODO
	//此处lastId被作作为静态成员存储 但是真正在集群这样是不可行的 
	//因为一旦设置更高的并发度 有多个当前Bolt的task分布在多个Workder中 
	//则这些Bolt用的lastId根本就不是一个
	//真正开发中，这个lastId应该存在外部存储中，例如 zookeeper redis 数据库...
	private static int lastId = 0;
	
	@Override
	public void finishBatch() {
		//--这个批中的所有的tuple都处理完，数据已经全部存在lmap中
		try {
			//--检测这个批是否是重发批
			if(id.getTransactionId().intValue()>lastId){//新批，正常处理
				//TODO 开启事务
				//将lmap中的所有数据 写入结果数据库中
				for(Map.Entry<String, Integer>entry:lmap.entrySet()){
					map.put(entry.getKey(), map.containsKey(entry.getKey()) 
											  ? map.get(entry.getKey())+1 : 1);
				}
				//发送数据
				//更新lastId为当前这个批的编号
				lastId = id.getTransactionId().intValue();
				//TODO 提交事务
				for(Map.Entry<String, Integer>entry : lmap.entrySet()){
					collector.emit(new Values(id,entry.getKey(),map.get(entry.getKey())));
				}
			}else{//重发批，直接抛弃
				System.err.println("===发现重发批["+id.getTransactionId()+"],直接抛弃===");
			}
		} catch (Exception e) {
			//TODO 回滚事务
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("txid","word","count"));
	}

}
