package cn.storm.wcdemo3;

import java.util.Map;

import backtype.storm.coordination.BatchOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBatchBolt;
import backtype.storm.transactional.TransactionAttempt;
import backtype.storm.tuple.Tuple;

public class TransReportBolt extends BaseBatchBolt <TransactionAttempt>{

	private TransactionAttempt id = null;
	
	@Override
	public void prepare(Map conf, TopologyContext context, BatchOutputCollector collector, TransactionAttempt id) {
		this.id = id;
	}

	@Override
	public void execute(Tuple tuple) {
		String word = tuple.getStringByField("word");
		int count = tuple.getIntegerByField("count");
		System.out.println("--id:["+id.getTransactionId()+"]--word:["+word+"],count:["+count+"]--");
	}

	@Override
	public void finishBatch() {
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	}

}
