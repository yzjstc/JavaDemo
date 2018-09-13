package cn.storm.wcdemo;

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

	@Override
	public void execute(Tuple input) {
		String word =input.getStringByField("word");
		int count = input.getIntegerByField("count");
		System.err.println("===单词数量发生变化：word["+word+"],count["+count+"]===");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
