package cn.storm.wcdemo2;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class ReportBolt extends BaseRichBolt {

	private OutputCollector collector = null;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	private Map<String,Integer> map = new HashMap<String, Integer>();
	@Override
	public void execute(Tuple input) {
		try {
			String word =input.getStringByField("word");
			int count = input.getIntegerByField("count");
			System.err.println("===单词数量发生变化：word["+word+"],count["+count+"]===");
			
			map.put(word, count);
			//当tuple处理成功时,调用ack,表示处理成功
			//后续没有操作了,所以不需要锚定
			collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
			//tuple处理失败,调用fail,表示处理失败
			collector.fail(input);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}
	
	/**
	 * cleanup方法用于数据都处理完,关闭Topology前执行
	 * 但是异常关闭,例如 kill -9 则无法调用该方法
	 */
	@Override
	public void cleanup() {
		System.out.println("~~~~~~~~~~~~~~~~~~~");
		for(Map.Entry<String, Integer>entry : map.entrySet()){
			System.err.println("--word:["+entry.getKey()+"],count:["+entry.getValue()+"]--");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~");
	}
}
