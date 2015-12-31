package com.example.electionsurvey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.example.electionsurvey.adapters.VoteForMlaListAdapter;
import com.example.electionsurvey.bean.MlaBean;
import com.example.electionsurvey.providers.Survey;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class VoteForMlaActivity extends Activity{

	private String selectedArea;
	private String  selectedArea_2;
	private ListView mlaListView;
	private String successfullyVoted;
	
	private List<MlaBean> mAllMlas;
	private static boolean state[];
	private VoteForMlaListAdapter mlaAdapter;
	
	private EditText mMlaText;
	
	
	private String TAG = "VoteForMlaActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vote_mla);
		selectedArea = getIntent().getStringExtra(SelectAreaActivity.AREA_KEY);
		selectedArea_2 = getIntent().getStringExtra(SelectAreaActivity.AREA_2_KEY);
		
		log("selected Area = " + selectedArea + " and selectedArea_2 = " + selectedArea_2 );
		
		mlaListView = (ListView) findViewById(R.id.mla_listview);
		mMlaText = (EditText)findViewById(R.id.input_mla_name);
		mAllMlas = new ArrayList<MlaBean>();
		
		
		
		successfullyVoted = getResources().getString(R.string.mla_voted_successfully);
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
		VoteTabActivity.tabHost.getTabWidget().getChildAt(2).setEnabled(true);
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}


	private void displayListView()
	{
		
		log("displayListView");
		
		String selection = "area_hindi like '" +  selectedArea + "'";
		
		Cursor cursor = getContentResolver().query(Survey.MlaData.CONTENT_URI, null, selection, null, null);
		
		createMlaList(cursor);
		
		state = new boolean[mAllMlas.size()];
		
		
		 mlaAdapter = new VoteForMlaListAdapter(this, R.layout.mla_list_row, mAllMlas, state);
		mlaListView.setAdapter(mlaAdapter);
		
		
		mlaListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
	}
	
	
	private void createMlaList(Cursor cursor) {
		// TODO Auto-generated method stub
		
		
		if(cursor != null && cursor.moveToFirst())
		{
			log("cursor is not null");
			do 
			{
			
			String mla = cursor.getString(cursor.getColumnIndexOrThrow(Survey.MlaData.MLA_HINDI));
			
			log("mla = " + mla);
			
			mAllMlas.add(new MlaBean(mla));					
			 
			}
			while(cursor.moveToNext());
			
		}	
		
		cursor.close();
		
	
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	return super.onCreateOptionsMenu(menu);
	}

	
	public void onUpdateMla(View view)
	{
		switch(view.getId())
		{
		
		case R.id.add_mla_name:
		/*		boolean add_update_success = addAndUpdateMla();
				
				if (add_update_success)
					{
					ClickPictureUtils.clickPicture();
					Toast.makeText(this, successfullyVoted, Toast.LENGTH_SHORT).show();
					
					}
				
				mMlaText.setText("");
				
				startThankYouActivity();*/
			
				break;
				
		case R.id.mla_update_button:
				boolean update_success = updateMla();
				
				
				if (update_success)
				{
					
					Toast.makeText(this, successfullyVoted, Toast.LENGTH_SHORT).show();
					startThankYouActivity();
				}
				
			//	startThankYouActivity();
				
				
				break;
		default:
				break;
		}
	}
	
	
	/*private boolean addAndUpdateMla()
	{
		String mla = addMlaToTable();
		
		if(mla != null)
		return updateMlaTable(mla);
		else
			Toast.makeText(this, getResources().getString(R.string.correct_your_choice), Toast.LENGTH_LONG).show();
		
		return false;
		
	}
	*/
	
	/*private String addMlaToTable() {
		// TODO Auto-generated method stub
		
	log("addMla");
	
	String inputMla = null;
			
	if(mMlaText.getText() != null && !"".equals(mMlaText.getText().toString()))
	inputMla = mMlaText.getText().toString();
	
	else return null;
	
	
	
	log("input mla = " + inputMla);
	
	
	String selection = "mla_hindi like '" + inputMla  + "'";
	Cursor cursor = getContentResolver().query(Survey.MlaData.CONTENT_URI, null, selection, null, null);
	
	if(cursor != null)
	{
		if (cursor.getCount() > 0)
		{
			Toast.makeText(this, getResources().getString(R.string.mla_already_in_list), Toast.LENGTH_LONG).show();
			inputMla = null;
		}
	
	
	else
	{
		ContentValues values = new ContentValues();
		values.put("mla_hindi", inputMla);
		
		
		Uri uri = getContentResolver().insert(Survey.MlaData.CONTENT_URI, values);
		
		log("received uri = "+ uri);
		
	
	}
	}
	cursor.close(); 
	return inputMla;
		
	}*/
	
	


	private boolean updateMla() {
		// TODO Auto-generated method stub
	
	String mla = findSelectedMla();	
	
	if(mla != null) 
		return updateMlaTable(mla);
	
	return false;
	}


	private String findSelectedMla() {
		// TODO Auto-generated method stub
		
		
		int checkedCount = 0;
		int position = -1;
		String selectedMla = null;
		
		
		for (int i = 0 ; i < mlaListView.getCount() ; i++)
		{
			if (state[i])
			{
				checkedCount++;
				position = i;
			}
		}
		
		
		if(checkedCount == 1 && position != -1)
			selectedMla = ((MlaBean)mlaListView.getItemAtPosition(position)).getMla();
			
		else
			Toast.makeText(this, "Please select only one Mla of your choice", Toast.LENGTH_LONG).show();
			
			
		return selectedMla;
	}
	
	
	private boolean updateMlaTable(String mla)
	{
		Boolean success = false;
		
		String selection = "mla_hindi = '" + mla  + "' AND area_hindi = '" +  selectedArea + "' AND area_2 = '" + selectedArea_2 + "'";
		Cursor cursor = getContentResolver().query(Survey.Mla.CONTENT_URI, null, selection, null, null);
		
		if(cursor != null && cursor.moveToFirst())
		{
			int votes = cursor.getInt(cursor.getColumnIndexOrThrow(Survey.Mla.VOTES));
			votes += 1;
			
			ContentValues values = new ContentValues();
			values.put("votes", votes);
			int update  = getContentResolver().update(Survey.Mla.CONTENT_URI, values, selection, null);
			 
			if(update > 0)
				success = true;
				 
		}
		
		else
		{
			log("inserting 1st time");
			
			int votes = 1;
			
			ContentValues values = new ContentValues();
			values.put("mla_hindi", mla);
			values.put("area_hindi", selectedArea);
			values.put("area_2", selectedArea_2);
			values.put("votes", votes);
			
			Uri uri = getContentResolver().insert(Survey.Mla.CONTENT_URI, values);
			
			log("received uri = "+ uri);
			
			if (uri != null)
				success = true;
		}
		
		cursor.close();
		
		return success;
	}
	
	
	private void startThankYouActivity()
	{
		Intent intent = new Intent(VoteForMlaActivity.this,ThankYouActivity.class);
		intent.putExtra(SelectAreaActivity.AREA_KEY, selectedArea);
		intent.putExtra(SelectAreaActivity.AREA_2_KEY, selectedArea_2);
		startActivity(intent);
	}


	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
	
}
