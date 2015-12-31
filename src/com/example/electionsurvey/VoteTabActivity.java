package com.example.electionsurvey;





import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class VoteTabActivity extends TabActivity{
	
	private String selectedArea;
	private String selectedArea_2;
	
	public static  TabHost tabHost;
	
	private String TAG = "VoteTabActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_vote);
		
		selectedArea = getIntent().getStringExtra(SelectAreaActivity.AREA_KEY);
		selectedArea_2 = getIntent().getStringExtra(SelectAreaActivity.AREA_2_KEY);
		
		log("received selected area = " + selectedArea + " and area_2 = " + selectedArea_2);
		
		
		initTab();
	}
	
	 @Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			
			log("onDestroy");
			
		//	ClickPictureUtils.releaseFrontCamera();
		
		}

	
	private void initTab()
	{
		
		tabHost = getTabHost();
		 
	       // setting Title and Icon for the Tab
	       Intent cmIntent = new Intent(this, VoteForCMActivity.class);
	       cmIntent.putExtra(SelectAreaActivity.AREA_KEY, selectedArea);
	       cmIntent.putExtra(SelectAreaActivity.AREA_2_KEY, selectedArea_2);
	       
	       //View cmWidgetView = createTabView(this,"CM");
		   TabSpec tabSpecCM = tabHost.newTabSpec("CM").
				   				setIndicator("CM", getResources().getDrawable(R.drawable.icon_cm_tab)).
				   				setContent(cmIntent);
	
	       
	 
	        // Tab for Party
	        Intent partyIntent = new Intent(this, VoteForPartyActivity.class);
	        partyIntent.putExtra(SelectAreaActivity.AREA_KEY, selectedArea);
	        partyIntent.putExtra(SelectAreaActivity.AREA_2_KEY, selectedArea_2);
	        //View partyWidgetView = createTabView(this,"PARTY");
			TabSpec tabSpecParty = tabHost.newTabSpec("PARTY").
								   setContent(partyIntent).
								   setIndicator("PARTY", getResources().getDrawable(R.drawable.icon_party_tab));
	 
	        // Tab for MLA
	        Intent mlaIntent = new Intent(this, VoteForMlaActivity.class);
	        mlaIntent.putExtra(SelectAreaActivity.AREA_KEY, selectedArea);
	        mlaIntent.putExtra(SelectAreaActivity.AREA_2_KEY, selectedArea_2);
	       // View mlaWidgetView = createTabView(this, "MLA");
	        TabSpec tabSpecMla =tabHost.newTabSpec("MLA").
	        					setContent(mlaIntent).
	        					setIndicator("MLA", getResources().getDrawable(R.drawable.icon_mla_tab));
	        
	 
	        // Adding all TabSpec to TabHost
	        tabHost.addTab(tabSpecCM); 
	        tabHost.addTab(tabSpecParty);
	        tabHost.addTab(tabSpecMla);
	        
	        log("starting the front camera");
	        ClickPictureUtils.startFrontCamera();
	        
	        VoteTabActivity.tabHost.getTabWidget().getChildAt(0).setEnabled(false);
	        VoteTabActivity.tabHost.getTabWidget().getChildAt(1).setEnabled(false);
	        VoteTabActivity.tabHost.getTabWidget().getChildAt(2).setEnabled(false);
	}
	
	

	

	
	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
}
