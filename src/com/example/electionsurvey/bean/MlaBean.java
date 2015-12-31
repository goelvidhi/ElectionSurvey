package com.example.electionsurvey.bean;

public class MlaBean {

	
	private String mla;
	
	public MlaBean(String mlaName)
	{
		mla = mlaName;
	}
	
	public String getMla() {
		return mla;
	}
	public void setMla(String mla) {
		this.mla = mla;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	private boolean isChecked;
}
