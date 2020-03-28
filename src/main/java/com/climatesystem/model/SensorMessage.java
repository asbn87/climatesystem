package com.climatesystem.model;

public class SensorMessage {

	private String mac;
	private String date;
	private String tvoc;
	private String co2;
	private String temp;
	private String hum;
	
	public String getMac() {
		return mac;
	}
	
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTvoc() {
		return tvoc;
	}
	
	public void setTvoc(String tvoc) {
		this.tvoc = tvoc;
	}
	
	public String getCo2() {
		return co2;
	}
	
	public void setCo2(String co2) {
		this.co2 = co2;
	}
	
	public String getTemp() {
		return temp;
	}
	
	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public String getHum() {
		return hum;
	}
	
	public void setHum(String hum) {
		this.hum = hum;
	}
}
