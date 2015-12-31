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

public class LoginActivity extends Activity {
	
	
	private EditText inputUsername;
	private EditText inputPassword;
	
	private String validUsername;
	private String validPassword;
	
	private SharedPreferences userPreference;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		inputUsername = (EditText)findViewById(R.id.input_username);
		inputPassword = (EditText) findViewById(R.id.input_password);
		
		validUsername = getResources().getString(R.string.valid_username);
		validPassword = getResources().getString(R.string.valid_password);
		
		
		userPreference = getSharedPreferences("UserPref", MODE_PRIVATE);
		
		
		boolean prefValid = validateUserPreference();
		
		
	// Enable this code when in production
		
		if(prefValid)
			{
			startSelectAreaActivity();
			finish();
			}
		
		
		
}
	
	private boolean validateUserPreference() {
		// TODO Auto-generated method stub
	
		String username = userPreference.getString("username", "");
		String password = userPreference.getString("password", "");
		
		if(validUsername.equals(username) && validPassword.equals(password))
			return true;
		
		return false;
		
		
	}

	public void onClick(View view)
	{
		switch(view.getId())
		{
		
		case R.id.login:
			
			boolean valid = validateInput();
		
			if(valid)
			{
			startSelectAreaActivity();
			finish();
			}
			else
			Toast.makeText(this, getResources().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
			
			break;
		}
	}

	private boolean validateInput() {
		// TODO Auto-generated method stub
		
		String username = inputUsername.getText().toString();
		String password = inputPassword.getText().toString();
		
		if(validUsername.equals(username) && validPassword.equals(password))
	
		{
			SharedPreferences.Editor editor = userPreference.edit();
			editor.putString("username", username);
			editor.putString("password", password);
			editor.commit();
			return true;
			}
		
		
			return false;
		
	}

	private void startSelectAreaActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(LoginActivity.this, SelectAreaActivity.class);
		startActivity(intent);
		
	}
	
}
