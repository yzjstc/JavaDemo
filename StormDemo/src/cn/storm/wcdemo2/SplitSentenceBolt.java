package cn.storm.wcdemo2;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SplitSentenceBolt extends BaseRichBolt{

	private OutputCollector collector = null;
	
	/**
	 * 初始化的方法
	 * 在当前组件被初始化的时候被调用,执行初始化相关操作
	 * (根据以往经验,大多数的组件javaAPI的prepare方法一般都是用于初始化,只在初始化的时候执行一次)
	 * 
	 * stormConf: 当前Topology的配置信息
	 * context: 当前组件运行的上下文环境信息
	 * collector: 可以用来发送tuple,可以在任意位置发送tuple,此对象线程安全
	 * 通常保存到类内部作为成员变量,方便在其他地方访问
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector=collector;
	}

	/**
	 * 处理上游发送的tuple,每个tuple都会进入一次此方法进行处理
	 * 处理过后可以发送零个或多个tuple给后续的组件
	 * 如果需要,也可以不立即处理tuple,而是先持有tuple再在后续需要时处理
	 * input: 当前传入的要处理的tuple
	 */
	private boolean flag = true;//只模拟一次标记
	@Override
	public void execute(Tuple input) {
		try {
			//将每次取出的符合tuple基本信息的对象
			String sentence = input.getStringByField("sentence");
			
			if(flag && "Welcome to New York".equals(sentence)){
				flag = false;
				throw new RuntimeException("故意抛出一个异常,模拟数据丢失的情况");
			}
			String[] words = sentence.split(" ");
			for (String word : words){
				//第一个参数是Tuple anchor
				//锚定: 表示将子tuple和父tuple的关系维系在collector内部
				//在demo1中,此处没有做锚定,只发送Values构造的Tuple对象,因为不对数据丢失做处理
				collector.emit(input,new Values(word));
			}
			//当tuple处理成功,调用ack,表示处理成功
			collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
			//当tuple处理失败,调用fail,表示处理失败
			collector.fail(input);
		}
	}

	/**
	 * 用来声明当前组件输出的tuple的基本信息
	 * declare: 所有的组件如果需要发送tuple,都必须先声明再发送,此对象就是用来声明tuple的基本信息
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
