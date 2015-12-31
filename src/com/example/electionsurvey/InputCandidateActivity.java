package com.example.electionsurvey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.electionsurvey.adapters.InputCandidateListAdapter;
import com.example.electionsurvey.providers.Survey;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InputCandidateActivity extends Activity {
	
	
	//private SharedPreferences userPreference;
	private String selectedArea;
	private TextView selectedConstituencyView;
	
	private ListView candidatelistView;
	private List<String> candidateList;
	private InputCandidateListAdapter candidateListAdapter;
	
	private String TAG = "InputCandidateActivity";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_candidate);
		
		selectedConstituencyView = (TextView) findViewById(R.id.textview_constituency);
		
		
		selectedArea = getIntent().getStringExtra(SelectAreaActivity.AREA_KEY);
		candidatelistView = (ListView)findViewById(R.id.candidate_listview);
		
		candidateList = new ArrayList<String>();
		
		selectedConstituencyView.setText(selectedArea);
		
		displayListView();
		
}
	
	
	private void displayListView()
	{
		
		createEmptyCandidateList();
		
		candidateListAdapter = new InputCandidateListAdapter(this, R.layout.candidate_row, candidateList);
		candidatelistView.setAdapter(candidateListAdapter);
		candidatelistView.setItemsCanFocus(true);
		candidatelistView.setSelectionAfterHeaderView();
			
	}
	
	

	

	private void createEmptyCandidateList() {
		// TODO Auto-generated method stub
	
		for (int i = 0; i < 10 ; i++)
		{
			String candidate = new String("");
			candidateList.add(candidate);
		}
		
	}


	public void onClick(View view)
	{
		switch(view.getId())
		{
		
		case R.id.go_next_4:
			storeCandidateName();
			startLoginActivity();
			
			break;
			
		default: break;
		}
	}



	private void storeCandidateName() {
		// TODO Auto-generated method stub
		
		clearTable();
		addCandidateToTable();
		
	}


	


	private void clearTable() {
		// TODO Auto-generated method stub
	
		
		String selection = "area_hindi like '" +  selectedArea + "'";
		
		int rows_deleted = getContentResolver().delete(Survey.MlaData.CONTENT_URI, selection, null);
		
		log("rows_deleted = " + rows_deleted);
	}


	

	private void addCandidateToTable() {
		// TODO Auto-generated method stub
		log("addCandidateToTable");
		
		String inputCandidate = null;
				
	
		
		Iterator<String> i = candidateList.iterator();
		
		while (i.hasNext())
		{
			inputCandidate = i.next().toString();
			
			if(!"".equals(inputCandidate))
			{
				log("inserting candidate with name as " + inputCandidate);
				insertCandidate(inputCandidate);
			}
		}
			
	}
	
	private void insertCandidate(String inputCandidate) {
		// TODO Auto-generated method stub
		
		String selection = "mla_hindi like '" + inputCandidate  + "' AND area_hindi like '" +  selectedArea + "'";
		Cursor cursor = getContentResolver().query(Survey.MlaData.CONTENT_URI, null, selection, null, null);
		
		if(cursor != null)
		{
			if (cursor.getCount() > 0)
			{
				Toast.makeText(this, getResources().getString(R.string.mla_already_in_list), Toast.LENGTH_LONG).show();
			}
		
		
		else
		{
			ContentValues values = new ContentValues();
			values.put("mla_hindi", inputCandidate);
			values.put("area_hindi", selectedArea);
			
			
			Uri uri = getContentResolver().insert(Survey.MlaData.CONTENT_URI, values);
			
			log("received uri = "+ uri);
			
		
		}
		}
		cursor.close(); 
	}



	
	
	private void startLoginActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(InputCandidateActivity.this, LoginActivity_2.class);
		startActivity(intent);
		
	}
	
	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
	
}
