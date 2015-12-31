package com.example.electionsurvey;

import java.util.ArrayList;
import java.util.List;

import com.example.electionsurvey.providers.Survey;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectAreaActivity extends Activity{
	
	private Spinner areaSpinner;
	private List<String> mAllAreas;
	private String selectedArea;
	private TextView currentAreaMLA;
	private TextView currentAreaParty;
	
	private LinearLayout mArea_2Layout;
	private EditText mArea_2EditView;
	private String selectedArea_2;
	
	private String mVolunteerName = null;
	
	private boolean isSelectionSet;
	private boolean mIsAdmin = false;
	
	private String TAG = "SelectAreaActivity";
	public static String AREA_KEY = "SELECTED_AREA";
	public static String AREA_2_KEY = "INPUT_AREA_2";
	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_area);
		areaSpinner = (Spinner)findViewById(R.id.area_spinner);
		currentAreaMLA = (TextView)findViewById(R.id.current_mla);
		currentAreaParty = (TextView)findViewById(R.id.current_party);
		
		mArea_2Layout = (LinearLayout)findViewById(R.id.layout_area_2);
		mArea_2EditView = (EditText)findViewById(R.id.input_area_2);
		
		mIsAdmin = getIntent().getBooleanExtra(LoginActivity_2.ADMIN_LOGIN_KEY,false);
		
		
		selectedArea = null;
		selectedArea_2 = null;
		isSelectionSet = false;
		
		log("check admin login = " + mIsAdmin);
		if(mIsAdmin)
		{
			log("Removing the input area-2 layout due to admin login");
			mArea_2Layout.setVisibility(View.GONE);
		}
		else
			readInputArea_2();
		
		loadAreaSpinner();
		addListenerOnAreaSelection();
		
	}

	

	private void loadAreaSpinner() {
		// TODO Auto-generated method stub
		
		
		
		Cursor cursor = getContentResolver().query(Survey.Constituency.CONTENT_URI, null, null, null, null);
		
		mAllAreas = createAreaList(cursor);
		
		ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mAllAreas);
		areaAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		
		
		areaSpinner.setAdapter(areaAdapter);
		
		
	}

	private List<String> createAreaList(Cursor cursor) {
		// TODO Auto-generated method stub
		
		
		List<String> areasList = new ArrayList<String> ();
		
		areasList.add(getResources().getString(R.string.choose_const_area));
		
		if(cursor != null && cursor.moveToFirst())
		{
			do 
			{
			String area = cursor.getString(cursor.getColumnIndexOrThrow(Survey.Constituency.AREA_HINDI));
			
			log("area = " + area);
			
			
			areasList.add(area);					
			 
			}
			while(cursor.moveToNext());
			
		}	
		
		cursor.close();
		return areasList;
		
	}
	
	
	private void addListenerOnAreaSelection() {
		// TODO Auto-generated method stub
		
		if(areaSpinner != null)
		{
			areaSpinner.setOnItemSelectedListener(areaItemSelectedListener);
		}
	
	}
	
	
	OnItemSelectedListener areaItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			// TODO Auto-generated method stub
			log("item selected at pos " + pos);
		selectedArea = 	parent.getItemAtPosition(pos).toString();
		
		if(pos > 0)
		{	
		isSelectionSet =true;
		loadAreaDetails();
		}
		
		
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	private void loadAreaDetails()
	{
		
		String selection = Survey.Constituency.AREA_HINDI + " = '" + selectedArea + "'";
		
		Cursor cursor = getContentResolver().query(Survey.Constituency.CONTENT_URI, null, selection, null, null);
		
		
		if(cursor != null && cursor.moveToFirst())
		
		{
		String mla_hindi = cursor.getString(cursor.getColumnIndexOrThrow(Survey.Constituency.MLA_HINDI));
		String party_hindi = cursor.getString(cursor.getColumnIndexOrThrow(Survey.Constituency.PARTY_HINDI));
		
		String current_mla = getResources().getString(R.string.current_mla) + " - " + mla_hindi;
		String current_party = getResources().getString(R.string.current_party) + " - " + party_hindi;
		
		log("current mla is = " + current_mla + "and current party is = " + current_party);
		
		currentAreaMLA.setText(current_mla);
		currentAreaParty.setText(current_party);
		
		}
		
	
	}
	
	private String readInputArea_2()
	{
		
		if(mArea_2EditView != null && mArea_2EditView.getText() != null)
			return mArea_2EditView.getText().toString();
		
		
			return null;
		
	}
	
	
	public void onClick(View view)
	{
		switch(view.getId())
		{
		case R.id.go_next_3:
			
			selectedArea_2 = readInputArea_2();
			log("selected area = " + selectedArea + " and selectedArea_2 = " + selectedArea_2);
			
			if(isSelectionSet)
			{
				if(mIsAdmin)
			      startInputCandidateActivity();
				else
				{
					if(selectedArea_2 != null)
					startVoteTabActivity();
					else
						Toast.makeText(this, "Please input the area to proceed", Toast.LENGTH_LONG).show();	
				}	
			}
			else
			Toast.makeText(this, "Please select a constituency area to proceed", Toast.LENGTH_LONG).show();
			
			break;
			
		default: break;
		}
	}

	private void startInputCandidateActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SelectAreaActivity.this, InputCandidateActivity.class);
		intent.putExtra(AREA_KEY, selectedArea);
		intent.putExtra(AREA_2_KEY, selectedArea_2);
		startActivity(intent);
		
	}
	
	
	private void startVoteTabActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(SelectAreaActivity.this, VoteTabActivity.class);
		intent.putExtra(AREA_KEY, selectedArea);
		intent.putExtra(AREA_2_KEY, selectedArea_2);
		
		startActivity(intent);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.upload_server:
			startUploadService();
			return true;
			
			

		default:return super.onOptionsItemSelected(item);
			
		}
		
	}
	
	private void startUploadService() {
		// TODO Auto-generated method stub
	mVolunteerName = getIntent().getStringExtra(VolunteerEntryActivity.VOLUNTEER_NAME_KEY);
	Intent intent = new Intent(SelectAreaActivity.this,UploadService.class);
	intent.putExtra(VolunteerEntryActivity.VOLUNTEER_NAME_KEY, mVolunteerName);
	startService(intent);
		
	}



	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
}
