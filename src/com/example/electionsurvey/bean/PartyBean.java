package com.example.electionsurvey.bean;

public class PartyBean {

	
	private String party;
	private boolean isChecked;
	
	public PartyBean(String partyName)
	{
		party = partyName;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	

}
