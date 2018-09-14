package cn.storm.wcdemo3;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.transactional.TransactionalTopologyBuilder;
import backtype.storm.tuple.Fields;

public class TransWordCountTopology {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		//1.创建组件
		TransSentenceSpout sentenceSpout = new TransSentenceSpout();
		TransSplitSentenceBolt splitSentenceBolt = new TransSplitSentenceBolt();
		TransWordCountBolt wordCountBolt = new TransWordCountBolt();
		TransReportBolt reportBolt = new TransReportBolt();
		
		//2.创建拓扑构建者
		TransactionalTopologyBuilder builder 
			= new TransactionalTopologyBuilder("Trans_Wc_Toplogy", "Trans_Sentence_Spout", sentenceSpout);
		builder.setBolt("Trans_Split_Sentence_Bolt", splitSentenceBolt)
			.shuffleGrouping("Trans_Sentence_Spout");
		builder.setCommitterBolt("Trans_Word_Count_Bolt", wordCountBolt)
			.fieldsGrouping("Trans_Split_Sentence_Bolt", new Fields("word"));
		builder.setBolt("Trans_Report_Bolt", reportBolt)
			.globalGrouping("Trans_Word_Count_Bolt");
		
		//3.创建拓扑
		StormTopology topology = builder.buildTopology();
		
		//4.提交集群执行
//		Config conf = new Config();
//		StormSubmitter.submitTopology("Trans_Wc_Toplogy", conf, topology);
		
		//4.在本地集群模拟运行
		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();
		cluster.submitTopology("Trans_Wc_Toplogy", conf, topology);
		
		Thread.sleep(10 * 1000);
		cluster.killTopology("Trans_Wc_Toplogy");
		cluster.shutdown();
		
		/*
		 * 打成jar包, 到虚拟机执行java -jar jar包
		 */
	}
}
