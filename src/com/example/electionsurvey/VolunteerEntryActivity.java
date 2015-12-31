package com.example.electionsurvey;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VolunteerEntryActivity extends Activity {
	
	
	private TextView mVolunteerView;
	private EditText mInputVolunteer;
	private boolean mIsAdmin = false;
	private String mVolunteerName = null;
	
	public static String VOLUNTEER_NAME_KEY = "VOLUNTEER_NAME_KEY";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volunteer);
		
		String volunteerType = null;
		
		mVolunteerView = (TextView) findViewById(R.id.volunteer_view);
		mInputVolunteer = (EditText)findViewById(R.id.input_volunteer);
		
		mIsAdmin = getIntent().getBooleanExtra(LoginActivity_2.ADMIN_LOGIN_KEY, false);
		
		if(mIsAdmin)
		volunteerType = getResources().getString(R.string.admin);
		else
		volunteerType = getResources().getString(R.string.guest);
		
		mVolunteerView.setText(volunteerType);
}
	

	public void onClick(View view)
	{
		switch(view.getId())
		{
		
		case R.id.go_next_2:
			mVolunteerName = readVolunteerName();
			startSelectAreaActivity();
			break;
			
		default: break;
		}
	}



	private String readVolunteerName() {
		// TODO Auto-generated method stub
		if(mInputVolunteer != null && mInputVolunteer.getText() != null)
		return mInputVolunteer.getText().toString();
		
		return null;
		
		
	}


	private void startSelectAreaActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(VolunteerEntryActivity.this, SelectAreaActivity.class);
		intent.putExtra(LoginActivity_2.ADMIN_LOGIN_KEY, mIsAdmin);
		if(!mIsAdmin)
			intent.putExtra(VOLUNTEER_NAME_KEY, mVolunteerName);
		startActivity(intent);
		
	}
	
	
}
