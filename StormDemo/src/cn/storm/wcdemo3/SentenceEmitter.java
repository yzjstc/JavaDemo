package cn.storm.wcdemo3;

import java.math.BigInteger;

import backtype.storm.coordination.BatchOutputCollector;
import backtype.storm.transactional.ITransactionalSpout.Emitter;
import backtype.storm.transactional.TransactionAttempt;
import backtype.storm.tuple.Values;

public class SentenceEmitter implements Emitter<SentenceMetaData> {

	/**
	 * 根据传入的事务编号 和 元数据对象 组织一个批发送
	 * 要保证对于同一个批的编号,要保证发送的数据必须一模一样
	 * ,为了保证这一点,通常需要在Emitter内部保留已经发送的批的数据一段时间
	 * ,直到批完成了整个处理才可以删除
	 * 要注意,所有发送的tuple第一个字段要留给批编号
	 * tx:storm出入的批的编号,由storm自动生成
	 * coordinatorMeta:批的元数据信息,来自于Coordinator
	 * collector:用来发送tuple的对象
	 */
	@Override
	public void emitBatch(TransactionAttempt tx, SentenceMetaData coordinatorMeta, BatchOutputCollector collector) {
		int begin = coordinatorMeta.getBegin();
		int end = coordinatorMeta.getEnd();
		for(int i = begin;i<end;i++){
			String sentence = SentenceDB.getData()[i];
			collector.emit(new Values(tx,sentence));
		}
	}

	/**
	 * 当一个批完成了整个处理流程 此方法会被调用
	 * 主要在这个方法中 清理之前缓存的批相关的数据
	 */
	@Override
	public void cleanupBefore(BigInteger txid) {
		
	}

	/**
	 * 在当前组件销毁之前会调用的方法 
	 * 主要用来释放资源
	 */
	@Override
	public void close() {
		
	}

}