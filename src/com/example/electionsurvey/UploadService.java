package com.example.electionsurvey;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class UploadService extends Service {
	
	private URI uploadUri;
	private DefaultHttpClient mHttpClient;
	private String mVolunteerName = null;
	private static final int UPLOAD_RESPONSE = 1;
	private String TAG = "UploadService";
	
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		log("onCreate");
		try {
			uploadUri = new URI("http://polibrands.com/ElectionSurvey/uploads/index.php");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		log("onStartCommand");
		mVolunteerName = intent.getStringExtra(VolunteerEntryActivity.VOLUNTEER_NAME_KEY);
		log("mVolunteerName = " + mVolunteerName);
		
		new UpdateThread().start();
		
		return super.onStartCommand(intent, flags, startId);
		
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	class UpdateThread extends Thread
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			log("Thread started in new instance");
			//uploadImages();
			uploadDatabases();
			
		}

		
	}
	


	private void uploadImages() {
		// TODO Auto-generated method stub
		String filepath = getFilePath();
		doUpload(filepath,"telephony.db");
	}
	
	private void uploadDatabases() {
		// TODO Auto-generated method stub
		String filepath = getFilePath();
		doUpload(filepath,"electionsurvey.db");
	}
	
	private String getFilePath()
	{
		 PackageInfo packageInfo;
		try {
			packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			String dataDir = packageInfo.applicationInfo.dataDir;
			String dbFilePath = dataDir + "/databases/electionsurvey.db";
			return dbFilePath;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		return null;
	}
	
	
	 
	public void doUpload(String filepath, String filename) {
	
		
		 try {
			 HttpParams params = new BasicHttpParams();
		     params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		     mHttpClient = new DefaultHttpClient(params);

	            HttpPost httppost = new HttpPost(uploadUri);

	            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
	            multipartEntity.addPart("username", new StringBody(mVolunteerName));
	            File file = new File(filepath);
				multipartEntity.addPart("file", new FileBody(file));

				
	            httppost.setEntity(multipartEntity);

	           HttpResponse response =  mHttpClient.execute(httppost);
	           
	           Message msg = new Message();
	           msg.obj = response;
	           msg.what = UPLOAD_RESPONSE;
	           handler.sendMessage(msg);
	          	    
	        } catch (Exception e) {
	            log(e.getLocalizedMessage());
	        }
	    }

	
	private Handler handler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
        	
        	switch(msg.what)
        	{
        	case UPLOAD_RESPONSE:
        		
        		HttpResponse response = (HttpResponse)msg.obj;
        		handleServerUploadResponse(response);
        		break;
        		
        		default: break;
        		
        	}
        }
    };
    
    private void handleServerUploadResponse(HttpResponse response)
    {
    	
   
   try {
    
    	if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) 
    	{
        	log( "Status is no success");
        	Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
        } 
    	else 
    	{
       HttpEntity r_entity = response.getEntity();
       String responseString = EntityUtils.toString(r_entity);
       log("UPLOAD response = " + responseString);
       Toast.makeText(getApplicationContext(), "Database is uploaded successfully", Toast.LENGTH_SHORT).show();
       }	
   }
   
   catch (IOException exception)
   {
	   log(exception.getLocalizedMessage());
   }
   
    }
	

         

	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
}
