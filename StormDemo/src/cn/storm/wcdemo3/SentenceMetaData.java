package cn.storm.wcdemo3;

import java.io.Serializable;

public class SentenceMetaData implements Serializable {
	private int begin;
	private int end;
	
	public SentenceMetaData() {
	}

	public SentenceMetaData(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
}