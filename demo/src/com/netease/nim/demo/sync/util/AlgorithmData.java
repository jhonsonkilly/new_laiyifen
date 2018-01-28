package com.netease.nim.demo.sync.util;


public class AlgorithmData {
	
	private String key;
	
	private String dataMing;
	
	private String dataMi;
	
	private boolean doDisplay =true;//true-闇�瑕佸仛鏄剧ず澶勭悊锛宖alse-涓嶉渶瑕佸仛鏄剧ず澶勭悊

	public boolean isDoDisplay() {
		return doDisplay;
	}

	public void setDoDisplay(boolean doDisplay) {
		this.doDisplay = doDisplay;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDataMing() {
		return dataMing;
	}

	public void setDataMing(String dataMing) {
		this.dataMing = dataMing;
	}

	public String getDataMi() {
		return dataMi;
	}

	public void setDataMi(String dataMi) {
		this.dataMi = dataMi;
	}	

}
