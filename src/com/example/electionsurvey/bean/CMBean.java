package com.example.electionsurvey.bean;

public class CMBean {

	
	private String cm;
	private boolean isChecked;
	
	public CMBean(String cmName)
	{
		cm = cmName;
	}

	public String getCm() {
		return cm;
	}

	public void setCm(String cm) {
		this.cm = cm;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	
}
