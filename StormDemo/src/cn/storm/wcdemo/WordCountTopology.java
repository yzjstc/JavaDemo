package cn.storm.wcdemo;

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
		builder.setSpout("Sentence_Spout", spout);//设置spout
		builder.setBolt("Split_Sentence_Bolt", splitSentenceBolt).shuffleGrouping("Sentence_Spout");//随机分组,每个task接收到的tuple数量一样
		//按字段分组,后面是字段,此处表示同一字段分到同一个task中
		builder.setBolt("Word_Count_Bolt", wordCountBolt).fieldsGrouping("Split_Sentence_Bolt", new Fields("word"));
		builder.setBolt("Report_Bolt", reportBolt).globalGrouping("Word_Count_Bolt");//全局分组,所有的tuple都会发送到一个JVM实例中
		
		//4.利用拓扑构建者创建拓扑
		StormTopology topology = builder.createTopology();
		
		//5.在本地集群中模拟运行拓扑
		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();
		cluster.submitTopology("Wc_Topology", conf, topology);
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//休一段时间,tuple都处理完,关闭本地集群
		cluster.killTopology("Wc_Topology");
		cluster.shutdown();
		
		//5.将拓扑提交到集群中运行
//		Config conf = new Config();
//		StormSubmitter.submitTopology("Wc_Topology", conf, topology);
	}
}
