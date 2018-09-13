package cn.storm.wcdemo2;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WordCountTopology {
	public static void main(String[] args) {
		//1.创建出所有组件
		SentenceSpout spout = new SentenceSpout();
		SplitSentenceBolt splitSentenceBolt = new SplitSentenceBolt();
		WordCountBolt wordCountBolt = new WordCountBolt();
		ReportBolt reportBolt = new ReportBolt();
		
		//2.创建拓扑构建者
		TopologyBuilder builder = new TopologyBuilder();
		
		//3.向拓扑构建者告知 拓扑结构
		//分组方式必须指定,否则bolt无法正确地接收spout
		builder.setSpout("Sentence_Spout", spout);//设置spout
		builder.setBolt("Split_Sentence_Bolt", splitSentenceBolt).shuffleGrouping("Sentence_Spout");//随机分组,每个task接收到的tuple数量一样
		//按字段分组,后面是字段,此处表示同一字段分到同一个task中
		builder.setBolt("Word_Count_Bolt", wordCountBolt).fieldsGrouping("Split_Sentence_Bolt", new Fields("word"));
		builder.setBolt("Report_Bolt", reportBolt).globalGrouping("Word_Count_Bolt");//全局分组,所有的tuple都会发送到一个JVM实例中
		
		//除了可以指定分组方式外,还可以指定spout和bolt的线程个数和每个线程中启动的Task数量,
		//默认一个线程一个Task
//		builder.setSpout("Sentence_Spout", spout, 3);
		/*
		 * 但是以下这个情况就有所不同了
		 * 在指定线程数后,再指定任务数,意思是Storm会将任务分配到各个线程中执行,如果线程多于任务,那么多于的线程也不会再额外地启动更多的Task实例
		 */
//		builder.setSpout("Sentence_Spout", spout, 3).setNumTasks(2);
//		builder.setBolt("Split_Sentence_Bolt", splitSentenceBolt, 2).setNumTasks(3);
		
		//4.利用拓扑构建者创建拓扑
		StormTopology topology = builder.createTopology();
		
		//5.在本地集群中模拟运行拓扑
		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();
		conf.setMessageTimeoutSecs(10);//设置传输超时时间
		/*
		 * 通过代码为指定的Topology配置多个Worker
		 * 不过在单机测试模式下,配置worker数量没有意义
		 * 而默认情况下(不修改配置文件),一个node服务器可以有4个worker节点
		 */
//		conf.setNumWorkers(2);
		cluster.submitTopology("Wc_Topology", conf, topology);
		//休一段时间,tuple都处理完,关闭本地集群
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cluster.killTopology("Wc_Topology");
		cluster.shutdown();
		
		//5.将拓扑提交到集群中运行
//		Config conf = new Config();
//		StormSubmitter.submitTopology("Wc_Topology", conf, topology);
	}
}
