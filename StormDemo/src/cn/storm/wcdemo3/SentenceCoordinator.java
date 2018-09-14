package cn.storm.wcdemo3;

import java.math.BigInteger;

import backtype.storm.transactional.ITransactionalSpout.Coordinator;

public class SentenceCoordinator implements Coordinator<SentenceMetaData> {
	//--批大小
	private int batchSize = 3;
	//--是否有新的批
	private boolean hasMoreBatch = true;
	/**
	 * 当isReady方法返回true时，storm回来调用当前方法，要求真的组织一个批的元数据返回
	 * 这个元数据对象中应该包含任何可能需要的信息，必须保证能够基于这些信息组织一个批来发送，
	 * 且在批出问题时，应该可以利用这个元数据对象重新组织出一模一样的批出来
	 */
	@Override
	public SentenceMetaData initializeTransaction(BigInteger txid, SentenceMetaData prevMetadata) {
		//--begin = 是第一个批吗 ? 0 : 上一个批的结尾
		int begin = prevMetadata == null 
				? 0 : prevMetadata.getEnd();
		//--end = begin + 批大小 < 数组长度 ? begin+批大小 : 数组的结尾
		int end = begin+batchSize < SentenceDB.getData().length 
				? begin+batchSize : SentenceDB.getData().length;
		//--是否还有新的批 = end 是否等于 数组的结尾
		hasMoreBatch = end != SentenceDB.getData().length;
		//--将begin和end 组织为SentenceMetaData返回
		return new SentenceMetaData(begin, end);
	}

	/**
	 * storm不停的在一个单一循环中调用此方法，询问是否准备好发送一个新的批
	 * 如果准备好了发送一个新的批，就返回true,否则返回false
	 * 也可以在此方法中睡眠一段事件，以便于在一段时间后发送当前批
	 */
	@Override
	public boolean isReady() {
		return hasMoreBatch;
	}

	/**
	 * 当前Coordinator被销毁之前调用
	 * 可以用来释放一些资源
	 */
	@Override
	public void close() {
	}

}