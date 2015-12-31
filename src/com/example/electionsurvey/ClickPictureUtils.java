package com.example.electionsurvey;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;

import android.os.Environment;
import android.util.Log;

import android.widget.Button;

public class ClickPictureUtils {

		private static Camera mCamera;
		private static CameraInfo mCameraInfo;
		
		
	    
	  static  void startFrontCamera()
		{
		  Log.d("TestCamera", "startFrontCamera");
		 
	 	int cameracount = 0;
	 	Camera cam = null;
	    	
	    	cameracount = Camera.getNumberOfCameras();
	    	mCameraInfo = new CameraInfo();
	    	
	    for (int i=0; i < cameracount ; i++)
	    {
	      	Camera.getCameraInfo(i, mCameraInfo);
	    
	      	if(mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
	    	{
	    		try
	    		{
	    		 cam = Camera.open(i);
	    		 Log.d("TestCamera", "front camera open success");
	    		}
	    		catch(RuntimeException e)
	    		{
	    		Log.e("TestCamera", "Camera open failed and the exception is " + e.getMessage());
	    		}
	    	}
	    	
	    }
	    
	    if(cam != null) 
	    	mCamera =  cam;
		  
	    
		}
	    
	    
	private static File getOutputMediaFile() {
			
			
			String appPath = "ElectionSurvey" +  File.separator + new SimpleDateFormat("yyyyMMMdd").format(new Date());
	        File mediaStorageDir = new File( Environment.getExternalStorageDirectory().getAbsoluteFile(),appPath);
	        
	        if (!mediaStorageDir.exists()) {
	        	
	            if (!mediaStorageDir.mkdirs()) {
	                Log.d("MyCameraApp", "failed to create directory");
	                return null;
	            }
	        }
	        // Create a media file name
	        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
	                .format(new Date());
	        File mediaFile;
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                + "IMG_" + timeStamp + ".jpg");

	        return mediaFile;
	    }


	static void clickPicture()
	{
		 if(mCamera != null)
		 {
			 Log.d("TestCamera", "Taking picture now");
			    mCamera.takePicture(null, null, jpegCallback);
		 }
		 else
			 Log.e("TestCamera", "camera instance is null");
			    
	}
	    
	    
	    static void releaseFrontCamera() {
			// TODO Auto-generated method stub
	    	 
	    	Log.d("TestCamera", "Releasing Front camera");
	    	if(mCamera != null)
	    	mCamera.release();
		}

		
	   static PictureCallback jpegCallback = new PictureCallback() {
			
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				// TODO Auto-generated method stub
				
				File pictureFile = getOutputMediaFile();
				
				Log.d("TestCamera", "Writing the file to device");
				if(pictureFile == null) return;
				
				try
				{
					Log.d("TestCamera", "Writing the finally file to device");
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
			    }
				
				catch(FileNotFoundException e){
					
				}
				catch (IOException e)
				{
					
				}
			}
		};
		
		
	}


