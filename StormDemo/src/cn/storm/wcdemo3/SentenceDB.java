package cn.storm.wcdemo3;

public class SentenceDB {
	/*
	 * 该类用来模拟数据的来源, 实际生成中,数据来源可以来自于kafka, 数据库等
	 */
	private static String [] sentence = {
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
	private SentenceDB() {
	}
	
	public static String [] getData(){
		return sentence;
	}
}
