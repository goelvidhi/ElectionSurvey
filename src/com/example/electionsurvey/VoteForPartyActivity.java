package com.example.electionsurvey;

import java.util.ArrayList;
import java.util.List;

import com.example.electionsurvey.adapters.VoteForPartyListAdapter;
import com.example.electionsurvey.bean.PartyBean;
import com.example.electionsurvey.providers.Survey;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class VoteForPartyActivity extends Activity{
	
	private String selectedArea;
	private String selectedArea_2;
	private ListView partyListView;
	private String successfullyVoted;
	
	private List<PartyBean> mAllPartys;
	private static boolean state[];
	
	private Integer images[] = new Integer[]{
		R.drawable.inc,
		R.drawable.bjp,
		R.drawable.bsp,
		R.drawable.cpm,
		R.drawable.in_samaj,
		R.drawable.administrator_icon,
		R.drawable.aap,
		R.drawable.jago,
		R.drawable.administrator_icon,
		R.drawable.administrator_icon
	};
	
	private String TAG = "VoteForPartyActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_party);
		
		selectedArea = getIntent().getStringExtra(SelectAreaActivity.AREA_KEY);
		selectedArea_2 = getIntent().getStringExtra(SelectAreaActivity.AREA_2_KEY);
		
		log("selected Area = " + selectedArea + " and selectedArea_2 = " + selectedArea_2 );
		
		partyListView = (ListView) findViewById(R.id.party_listview);
		
		successfullyVoted = getResources().getString(R.string.party_voted_successfully);
		
		displayListView();
		
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
	


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		VoteTabActivity.tabHost.getTabWidget().getChildAt(1).setEnabled(true);
		
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	private void displayListView()
	{
		
		log("displayListView");
		Cursor cursor = getContentResolver().query(Survey.PartyData.CONTENT_URI, null, null, null, null);
		
		mAllPartys = createPartyList(cursor);
		
		state = new boolean[mAllPartys.size()];
		VoteForPartyListAdapter partyAdapter = new VoteForPartyListAdapter(this, R.layout.party_list_row, mAllPartys, state, images);
		partyListView.setAdapter(partyAdapter);
		
		partyListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
	}
	
	
	private List<PartyBean> createPartyList(Cursor cursor) {
		// TODO Auto-generated method stub
		
		List<PartyBean> partyList = new ArrayList<PartyBean> ();
		
		
		if(cursor != null && cursor.moveToFirst())
		{
			
			do 
			{
			String party = cursor.getString(cursor.getColumnIndexOrThrow(Survey.PartyData.PARTY_HINDI));
			
			log("party = " + party);
				
			partyList.add(new PartyBean(party));				
			 
			}
			while(cursor.moveToNext());
			
		}	
		return partyList;
	
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	return super.onCreateOptionsMenu(menu);
	}

	
	public void onUpdateParty(View view)
	{
		switch(view.getId())
		{
		case R.id.party_update_button:
				boolean success = updateParty();
				//ClickPictureUtils.clickPicture();
				
				if (success)
				{
					Toast.makeText(this, successfullyVoted, Toast.LENGTH_SHORT).show();	
				
				
				VoteTabActivity.tabHost.getTabWidget().getChildAt(1).setEnabled(false);
				VoteTabActivity.tabHost.setCurrentTab(2);
				}
				
				break;
		default:
				break;
		}
	}
	
	
	private boolean updateParty() {
		// TODO Auto-generated method stub
	
	String party = findSelectedParty();	
	
	if(party != null) 
		return updatePartyTable(party);
	
	return false;
	}


	private String findSelectedParty() {
		// TODO Auto-generated method stub
		
		
		int checkedCount = 0;
		int position = -1;
		String selectedParty = null;
		
		
		for (int i = 0 ; i < partyListView.getCount() ; i++)
		{
			if (state[i])
			{
				checkedCount++;
				position = i;
			}
		}
		
		
		if(checkedCount == 1 && position != -1)
			selectedParty = ((PartyBean)partyListView.getItemAtPosition(position)).getParty();
			
		else
			Toast.makeText(this, "Please select only one Party of your choice", Toast.LENGTH_LONG).show();
			
			
		return selectedParty;
	}
	
	
	private boolean updatePartyTable(String party)
	{
		Boolean success = false;
		
		String selection = "party_hindi = '" + party  + "' AND area_hindi = '" +  selectedArea + "' AND area_2 = '" + selectedArea_2 + "'";
		Cursor cursor = getContentResolver().query(Survey.Party.CONTENT_URI, null, selection, null, null);
		
		if(cursor != null && cursor.moveToFirst())
		{
			int votes = cursor.getInt(cursor.getColumnIndexOrThrow(Survey.Party.VOTES));
			votes += 1;
			
			ContentValues values = new ContentValues();
			values.put("votes", votes);
			int update  = getContentResolver().update(Survey.Party.CONTENT_URI, values, selection, null);
			 
			if(update > 0)
				success = true;
				 
		}
		
		else
		{
			log("inserting 1st time");
			
			int votes = 1;
			
			ContentValues values = new ContentValues();
			values.put("party_hindi", party);
			values.put("area_hindi", selectedArea);
			values.put("area_2", selectedArea_2);
			values.put("votes", votes);
			
			Uri uri = getContentResolver().insert(Survey.Party.CONTENT_URI, values);
			
			log("received uri = "+ uri);
			
			if (uri != null)
				success = true;
		}
		
		cursor.close();
		
		return success;
	}


	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
	
}
