package cn.storm.wcdemo2;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCountBolt extends BaseRichBolt {
	private OutputCollector collector = null;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	private Map<String,Integer> map = new HashMap<>();
	@Override
	public void execute(Tuple input) {
		try {
			String word = input.getStringByField("word");
			map.put(word, map.containsKey(word)?map.get(word)+1:1);
			//此处也要进行锚定,将子tuple和父tuple的关系维系在Controller内部
			collector.emit(input,new Values(word,map.get(word)));//发射的tuple可以携带多个元素
			//tuple处理成功,调用ack,表示处理成功
			collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
			//当tuple处理失败,调用fail,表示处理成功
			collector.fail(input);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//tuple的基本信息可以同时声明多个元素
		declarer.declare(new Fields("word","count"));
	}

}
