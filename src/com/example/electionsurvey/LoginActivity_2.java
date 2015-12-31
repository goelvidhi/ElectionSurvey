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

public class LoginActivity_2 extends Activity {
	
	
	//private SharedPreferences userPreference;
	public static String ADMIN_LOGIN_KEY = "IS_ADMIN_LOGIN";
	private boolean isAdmin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_2);
		
}
	

	public void onClick(View view)
	{
		switch(view.getId())
		{
		
		case R.id.admin_login:
			isAdmin = true;
			startVolunteerEntryActivity();
			
			
			break;
			
		case R.id.guest_login:
			isAdmin = false;
			startVolunteerEntryActivity();
			
			
			break;
			
			
		default: break;
		}
	}



	private void startVolunteerEntryActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(LoginActivity_2.this, VolunteerEntryActivity.class);
		intent.putExtra(ADMIN_LOGIN_KEY, isAdmin);
		startActivity(intent);
		
	}
	
	
	
}
