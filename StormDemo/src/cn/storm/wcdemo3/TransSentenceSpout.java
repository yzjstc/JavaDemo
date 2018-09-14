package cn.storm.wcdemo3;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseTransactionalSpout;
import backtype.storm.transactional.ITransactionalSpout.Emitter;
import backtype.storm.tuple.Fields;

public class TransSentenceSpout extends BaseTransactionalSpout<SentenceMetaData> {

	/**
	 * 获得组织批的元数据信息
	 * MetaData需要自己创建一个类,实现Serializable接口,记录批开始的位置,批结束的位置
	 * Coordinator是用来生成每个批处理的起始和结束位置的
	 */
	@Override
	public Coordinator<SentenceMetaData> getCoordinator(Map conf,TopologyContext context) {
		return new SentenceCoordinator();
	}

	/**
	 * 根据元数据信息,组织一个批进行发送
	 * 发送的批内容Emitter,需要实现Emitter<元数据信息的类>
	 */
	@Override
	public Emitter<SentenceMetaData> getEmitter(Map conf,TopologyContext context) {
		return new SentenceEmitter();
	}

	/**
	 * 发钱先声明发送批的tuple信息
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("txid","sentence"));
	}

}