package com.example.electionsurvey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ThankYouActivity extends Activity{
	
	private String TAG = "ThankYouActivity";
	private String selectedArea;
	private String selectedArea_2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thankyou);
		
		selectedArea = getIntent().getStringExtra(SelectAreaActivity.AREA_KEY);
		selectedArea_2 = getIntent().getStringExtra(SelectAreaActivity.AREA_2_KEY);
		
		log("selected Area = " + selectedArea + " and selectedArea_2 = " + selectedArea_2 );
		
		ClickPictureUtils.releaseFrontCamera();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thankyou, menu);
		
		return true;
	}
	
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {
		case R.id.poll_again:
			startVoteTabActivity();
			return true;
			
			

		default:return super.onOptionsItemSelected(item);
			
		}
		
	}
	
	
	/*public void onClick(View view)
	{
		switch(view.getId())
		{
		case R.id.poll_again:
			
			
			
			break;
			
		default: break;
		}
	}
*/

	
	
	private void startVoteTabActivity() {
		// TODO Auto-generated method stub
		
		log("startVoteTabActivity");
		Intent intent = new Intent(ThankYouActivity.this, VoteTabActivity.class);
		intent.putExtra(SelectAreaActivity.AREA_KEY, selectedArea);
		intent.putExtra(SelectAreaActivity.AREA_2_KEY, selectedArea_2);
		
		startActivity(intent);
		
		
	}
	
	
	
	
	private void log(String msg)
	{
		Log.d(TAG, msg);
	}

	

}
