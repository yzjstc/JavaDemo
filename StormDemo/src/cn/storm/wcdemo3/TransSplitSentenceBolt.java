package cn.storm.wcdemo3;

import java.util.Map;

import backtype.storm.coordination.BatchOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBatchBolt;
import backtype.storm.transactional.TransactionAttempt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * BaseBatchBolt会为每个批 都创建一个该类的对象来处理
 */
public class TransSplitSentenceBolt extends BaseBatchBolt<TransactionAttempt> {

	private BatchOutputCollector collector = null;
	private TransactionAttempt id = null;
	
	/**
	 * 初始化的方法
	 * 在当前组件被初始化时调用 执行初始化操作
	 * conf:代表当前集群配置
	 * context:代表上下文,即当前组件运行的环境
	 * collector:用来发送tuple的collector对象
	 * id:当前批的编号
	 */
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this.collector = collector;
		this.id = id;
	}

	/**
	 * 当前批中的每一个tuple都会进入一次此方法 ,来分别进行处理
	 */
	@Override  
	public void execute(Tuple tuple) {
		String sentence = tuple.getStringByField("sentence");
		String words [] = sentence.split(" ");
		for(String word : words){
			collector.emit(new Values(id,word));
		}
	}

	/**
	 * 当整个批中的所有的tuple都完成了处理 会最终调用一次此方法
	 */
	@Override
	public void finishBatch() {
		
	}

	/**
	 * 声明输出的tuple格式的方法
	 * declarer:用来来声明tuple格式的对象
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("txid","word"));
	}

}
