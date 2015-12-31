package com.example.electionsurvey;

import java.util.ArrayList;
import java.util.List;
import com.example.electionsurvey.adapters.VoteForCMListAdapter;
import com.example.electionsurvey.bean.CMBean;
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

public class VoteForCMActivity extends Activity{

	private String selectedArea;
	private String selectedArea_2;
	private ListView cmListView;
	private String successfullyVoted;

	
	private List<CMBean> mAllCMs;
	private static boolean state[];
	
	
	private String TAG = "VoteForCMActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_cm);
		
		
		selectedArea = getIntent().getStringExtra(SelectAreaActivity.AREA_KEY);
		selectedArea_2 = getIntent().getStringExtra(SelectAreaActivity.AREA_2_KEY);
		
		log("selected Area = " + selectedArea + "and selectedArea_2 = " + selectedArea_2);
		
		cmListView = (ListView) findViewById(R.id.cm_listview);
		
		successfullyVoted = getResources().getString(R.string.cm_voted_successfully);
			
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
		
		VoteTabActivity.tabHost.getTabWidget().getChildAt(0).setEnabled(true);
		
	}




	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}




	private void displayListView()
	{
		
		log("displayListView");
		Cursor cursor = getContentResolver().query(Survey.CMData.CONTENT_URI, null, null, null, null);
		
		mAllCMs = createCMList(cursor);
		
		state = new boolean[mAllCMs.size()];
		
		VoteForCMListAdapter cmAdapter = new VoteForCMListAdapter(this, R.layout.cm_list_row, mAllCMs,state);
		cmListView.setAdapter(cmAdapter);
		
		cmListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
	}
	
	
	private List<CMBean> createCMList(Cursor cursor) {
		// TODO Auto-generated method stub
		
		List<CMBean> cmList = new ArrayList<CMBean> ();
		
		
		if(cursor != null && cursor.moveToFirst())
		{
			log("cursor is not null");
			do 
			{
			
			String cm = cursor.getString(cursor.getColumnIndexOrThrow(Survey.CMData.CM_HINDI));
			
			log("cm = " + cm);
			
			cmList.add(new CMBean(cm));					
			 
			}
			while(cursor.moveToNext());
			
		}	
		return cmList;
	
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	return super.onCreateOptionsMenu(menu);
	}

	
	public void onUpdateCM(View view)
	{
		switch(view.getId())
		{
		case R.id.cm_update_button:
				boolean success = updateCM();
				
				
				if (success)
				{
					log("clicking now");
				ClickPictureUtils.clickPicture();
				Toast.makeText(this, successfullyVoted, Toast.LENGTH_SHORT).show();	
				
				VoteTabActivity.tabHost.getTabWidget().getChildAt(0).setEnabled(false);
				VoteTabActivity.tabHost.setCurrentTab(1);
				
			//	ClickPictureUtils.releaseFrontCamera();
				}
				
				break;
		default:
				break;
		}
	}
	
	
	private boolean updateCM() {
		// TODO Auto-generated method stub
	
	String cm = findSelectedCM();	
	
	if(cm != null) 
		return updateCMTable(cm);
	
	return false;
	}


	private String findSelectedCM() {
		// TODO Auto-generated method stub
		
		
		int checkedCount = 0;
		int position = -1;
		String selectedCM = null;
		
		
		for (int i = 0 ; i < cmListView.getCount() ; i++)
		{
			if (state[i])
			{
				checkedCount++;
				position = i;
			}
		}
		
		
		if(checkedCount == 1 && position != -1)
			selectedCM = ((CMBean)cmListView.getItemAtPosition(position)).getCm();
			
		else
			Toast.makeText(this, "Please select only one CM of your choice", Toast.LENGTH_LONG).show();
			
			
		return selectedCM;
	}
	
	
	private boolean updateCMTable(String cm)
	{
		Boolean success = false;
		
		String selection = "cm_hindi = '" + cm  + "' AND area_hindi = '" +  selectedArea + "' AND area_2 = '" + selectedArea_2 + "'";
		Cursor cursor = getContentResolver().query(Survey.CM.CONTENT_URI, null, selection, null, null);
		
		if(cursor != null && cursor.moveToFirst())
		{
			int votes = cursor.getInt(cursor.getColumnIndexOrThrow(Survey.CM.VOTES));
			votes += 1;
			
			ContentValues values = new ContentValues();
			values.put("votes", votes);
			int update  = getContentResolver().update(Survey.CM.CONTENT_URI, values, selection, null);
			 
			if(update > 0)
				success = true;
				 
		}
		
		else
		{
			log("inserting 1st time");
			
			int votes = 1;
			
			ContentValues values = new ContentValues();
			values.put("cm_hindi", cm);
			values.put("area_hindi", selectedArea);
			values.put("area_2", selectedArea_2);
			values.put("votes", votes);
			
			Uri uri = getContentResolver().insert(Survey.CM.CONTENT_URI, values);
			
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
