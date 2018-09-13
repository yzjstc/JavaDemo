package cn.storm.wcdemo2;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class SentenceSpout extends BaseRichSpout{
	
	String sentences[] ={
		"Walking through a crowd",
		"The village is aglow",
		"Kaleidoscopes of loud",
		"Heartbeats under coats",
		"Everybody here wanted something more",
		"Searching for a sound we hadn't",
		"Heard before",
		"And it said",
		"Welcome to New York",
		"It's been waiting for you",
		"Welcome to New York",
		"Welcome to New York",
		"Welcome to New York"
	};
	
	private SpoutOutputCollector collector = null;
	
	/**
	 * 初始化方法
	 * 在当前组件被初始化时调用,执行初始化相关操作
	 * conf:当前topology的配置信息
	 * context: 当前组件运行的上下文环境信息
	 * collector: 可以用来发送tuple,可以在任意位置发送tuple,这个对象线程安全
	 * 通常保存到类内部作为成员变量,方便在其他方法中访问
	 */
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;//作为成员变量,才能在其他地方进行使用
	}

	/**
	 * 用来声明当前组件输出的tuple的基本信息
	 * declare : 用来输出tuple基本信息的对象,所有的组件如果想要发送tuple,都必须先声明再发送
	 * 而此对象就是用来声明tuple的基本信息
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}	
	
	/**
	 * storm会不停地调用这个方法, 要求发送tuple
	 * 此方法不应该被阻塞,所以如果没有任何tuple要发送,直接返回即可
	 * 在底层其实就是一个单一的线程内循环不停地在调用此方法,所以如果真的没有任何tuple要发送
	 * 最好在这个方法中,睡眠一小段时间,以便于不至于浪费过多的CPU
	 */
	private int index = 0;//标志位,防止数组下标越界
	@Override
	public void nextTuple() {
		if(index < sentences.length){
			//emit方法将tuple发送
			//而Values的构造方法可以将数据转换成tuple
			//为了后续会调用的ack()和fail()需要用到msgId,Values的第二个参数作为了msgId使用
			collector.emit(new Values(sentences[index]),index);
			index++;
		}else{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
	/**
	 * 指定id的消息处理成功了,删除缓存数据
	 * 不仅需要spout发射成功,还需有后续的bolt也处理完成,才执行该方法
	 */
	@Override
	public void ack(Object msgId) {
		System.out.println(sentences[(int)msgId]+"被成功处理了");
	}
	
	/**
	 * 指定id的消息处理失败了,重发数据
	 * spout或者后续的bolt任意一个执行失败了,都会执行这个fail方法
	 */
	@Override
	public void fail(Object msgId) {
		System.out.println(sentences[(int)msgId]+"处理失败,等待重发.....");
		collector.emit(new Values(sentences[(int)msgId]),msgId);
	}
	
	/*
	 * 这同样也有一个问题,如果发送成功了,但是由于网络阻塞
	 * 然后数据被认为执行失败了,又被重新发送一次
	 * 则后面将处理可能不止一次该数据
	 */
}
