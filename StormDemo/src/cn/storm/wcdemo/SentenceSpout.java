package cn.storm.wcdemo;

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
			collector.emit(new Values(sentences[index]));
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
	
	/*
	 * 注意,该spout组件没有重写ack(),fail方法,在真实的开发环境中,有可能导致消息因为网络的原因
	 * 传送出现了意外而导致数据丢失
	 */
}
