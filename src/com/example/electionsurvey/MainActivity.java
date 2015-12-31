package com.example.electionsurvey;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	
	
	


	public void onClick(View view)
	{
		switch(view.getId())
		{
		case R.id.go_next_1:
			startLoginActivity();
		}
	}

	private void startLoginActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this, LoginActivity_2.class);
		startActivity(intent);
		
	}
}
