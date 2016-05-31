package com.gzcjteam.shundai;

public class RenWuInfo {
	
	private  String time;//任务发布时间
	private String kuaidiName;//快递名称
	private String sAddress;//收货地址
	private int  tupianId;//图片资源id
	private String id;
	private String launch_user_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getKuaidiName() {
		return kuaidiName;
	}
	public void setKuaidiName(String kuaidiName) {
		this.kuaidiName = kuaidiName;
	}
	public String getsAddress() {
		return sAddress;
	}
	public void setsAddress(String sAddress) {
		this.sAddress = sAddress;
	}
	public int getTupianId() {
		return tupianId;
	}
	public void setTupianId(int tupianId) {
		this.tupianId = tupianId;
	}

}
