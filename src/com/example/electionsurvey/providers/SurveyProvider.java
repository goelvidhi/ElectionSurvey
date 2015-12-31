package com.example.electionsurvey.providers;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;

import android.content.UriMatcher;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri; 
import android.util.Log;

public class SurveyProvider extends ContentProvider {

      
    private static final String TAG = "SurveyProvider";
    
    private static final String AUTHORITY = "com.example.electionsurvey";
    
    
	 private static final int URL_CONSTITUENCY = 1;
	 private static final int URL_CM_DATA = 2;
	 private static final int URL_PARTY_DATA = 3;
	 private static final int URL_MLA_DATA = 4;
	 
	 private static final int URL_CM = 5;
	 private static final int URL_PARTY = 6;
	 private static final int URL_MLA = 7;
	 
	 
	 private static final String BASE_PATH1 = "constituency";
	 private static final String BASE_PATH2 = "cmdata";
	 private static final String BASE_PATH3 = "partydata";
	 private static final String BASE_PATH4 = "mladata";
	 
	 private static final String BASE_PATH5 = "cm";
	 private static final String BASE_PATH6 = "party";
	 private static final String BASE_PATH7 = "mla";
	 
	 
	    
	public static final Uri CONTENT_URI1 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH1);
	public static final Uri CONTENT_URI2 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH2);
	public static final Uri CONTENT_URI3 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH3);
	public static final Uri CONTENT_URI4 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH4);
	
	public static final Uri CONTENT_URI5 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH5);
	public static final Uri CONTENT_URI6 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH6);
	public static final Uri CONTENT_URI7 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH7);
	
	
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/electionsurvey-constituency";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	        + "/electionsurvey-constituency";
    
	
	
	
    private SQLiteDatabase mSurveyDb;
     
    
    
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
      sURIMatcher.addURI(AUTHORITY, BASE_PATH1, URL_CONSTITUENCY);
      sURIMatcher.addURI(AUTHORITY, BASE_PATH2 , URL_CM_DATA);
      sURIMatcher.addURI(AUTHORITY, BASE_PATH3 , URL_PARTY_DATA);
      sURIMatcher.addURI(AUTHORITY, BASE_PATH4 , URL_MLA_DATA);
      
      
      sURIMatcher.addURI(AUTHORITY, BASE_PATH5 , URL_CM);
      sURIMatcher.addURI(AUTHORITY, BASE_PATH6 , URL_PARTY);
      sURIMatcher.addURI(AUTHORITY, BASE_PATH7 , URL_MLA);
      
    }
    
    
   private static final UriMatcher s_urlMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
    	s_urlMatcher.addURI(AUTHORITY, BASE_PATH1, URL_CONSTITUENCY);
    	s_urlMatcher.addURI(AUTHORITY, BASE_PATH2 , URL_CM_DATA);
    	s_urlMatcher.addURI(AUTHORITY, BASE_PATH3 , URL_PARTY_DATA);
    	s_urlMatcher.addURI(AUTHORITY, BASE_PATH4 , URL_MLA_DATA);
    	
    	s_urlMatcher.addURI(AUTHORITY, BASE_PATH5 , URL_CM);
    	s_urlMatcher.addURI(AUTHORITY, BASE_PATH6 , URL_PARTY);
    	s_urlMatcher.addURI(AUTHORITY, BASE_PATH7 , URL_MLA);
        
    }
    
    
    @Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
    	
    log("onCreate");
	mSurveyDb = new SurveyDatabase(getContext()).getWritableDatabase();
	return true;
	}



	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int uriType = sURIMatcher.match(uri);
		
	    SQLiteDatabase sqlDB = mSurveyDb;
	       
	    
	    int rowsAffected = 0;
	    switch (uriType) {
	    case URL_CONSTITUENCY:
	    	rowsAffected = sqlDB.delete(SurveyDatabase.CONSTITUENCY_TABLE,
	                selection, selectionArgs);
	        break;
	        
        case URL_MLA_DATA:
        {
        	rowsAffected = sqlDB.delete(SurveyDatabase.MLA_DATA_TABLE,
	                selection, selectionArgs);

                Log.d(TAG, "rows Affected =  " + rowsAffected);
                break;
        }

	    case URL_CM:
	       
	    	rowsAffected = sqlDB.delete(SurveyDatabase.CM_TABLE,
	                selection, selectionArgs);
	        
	        break;
	        
	        
	    case URL_PARTY:
		       
	    	rowsAffected = sqlDB.delete(SurveyDatabase.PARTY_TABLE,
	                selection, selectionArgs);
	        
	        break;
	        
	    case URL_MLA:
		       
	    	rowsAffected = sqlDB.delete(SurveyDatabase.MLA_TABLE,
	                selection, selectionArgs);
	        
	        break;
	        
	    default:
	        throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return rowsAffected;
	}
	
	
	

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		 switch (sURIMatcher.match(uri)) {
	        case URL_CONSTITUENCY:
	            return "vnd.android.cursor.dir/electionsurvey-constituency";

	        case URL_CM_DATA:
	            return "vnd.android.cursor.dir/electionsurvey-cmdata";

	        case URL_PARTY_DATA:
	            return "vnd.android.cursor.dir/electionsurvey-partydata";

	        case URL_MLA_DATA:
	            return "vnd.android.cursor.dir/electionsurvey-mladata";

	        case URL_CM:
	            return "vnd.android.cursor.item/electionsurvey-cm";    
	        

	        case URL_PARTY:
	            return "vnd.android.cursor.item/electionsurvey-party";

	        
	        case URL_MLA:
	            return "vnd.android.cursor.item/electionsurvey-mla";
	            
	            
	        default:
	            throw new IllegalArgumentException("Unknown URL " + uri);
	        }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
	      Uri result = null;

	      SQLiteDatabase db = mSurveyDb;
	        int match = sURIMatcher.match(uri);
	        boolean notify = false;
	        switch (match)
	        {
	            case URL_CONSTITUENCY:
	            {
	               long rowID = db.insert(SurveyDatabase.CONSTITUENCY_TABLE, null, values);
	                if (rowID > 0)
	                {
	                    result = ContentUris.withAppendedId(CONTENT_URI1, rowID);
	                    notify = true;
	                }

	                Log.d(TAG, "inserted " + values.toString() + " rowID = " + rowID);
	                break;
	            }


	            case URL_CM_DATA:
	            {
	            	   long rowID = db.insert(SurveyDatabase.CM_DATA_TABLE, null, values);
		                if (rowID > 0)
		                {
		                    result = ContentUris.withAppendedId(CONTENT_URI2, rowID);
		                    notify = true;
		                }

		                Log.d(TAG, "inserted " + values.toString() + " rowID = " + rowID);
		                break;
	            }
	            
	            
	            case URL_PARTY_DATA:
	            {
	            	   long rowID = db.insert(SurveyDatabase.PARTY_DATA_TABLE, null, values);
		                if (rowID > 0)
		                {
		                    result = ContentUris.withAppendedId(CONTENT_URI3, rowID);
		                    notify = true;
		                }

		                Log.d(TAG, "inserted " + values.toString() + " rowID = " + rowID);
		                break;
	            }


	            case URL_MLA_DATA:
	            {
	            	   long rowID = db.insert(SurveyDatabase.MLA_DATA_TABLE, null, values);
		                if (rowID > 0)
		                {
		                    result = ContentUris.withAppendedId(CONTENT_URI4, rowID);
		                    notify = true;
		                }

		                Log.d(TAG, "inserted " + values.toString() + " rowID = " + rowID);
		                break;
	            }
	            
	            case URL_CM:
	            {
	            	   long rowID = db.insert(SurveyDatabase.CM_TABLE, null, values);
		                if (rowID > 0)
		                {
		                    result = ContentUris.withAppendedId(CONTENT_URI5, rowID);
		                    notify = true;
		                }

		                Log.d(TAG, "inserted " + values.toString() + " rowID = " + rowID);
		                break;
	            }

	            case URL_PARTY:
	            {
	            	   long rowID = db.insert(SurveyDatabase.PARTY_TABLE, null, values);
		                if (rowID > 0)
		                {
		                    result = ContentUris.withAppendedId(CONTENT_URI6, rowID);
		                    notify = true;
		                }

		                Log.d(TAG, "inserted " + values.toString() + " rowID = " + rowID);
		                break;
	            }
	            
	            case URL_MLA:
	            {
	            	   long rowID = db.insert(SurveyDatabase.MLA_TABLE, null, values);
		                if (rowID > 0)
		                {
		                    result = ContentUris.withAppendedId(CONTENT_URI7, rowID);
		                    notify = true;
		                }

		                Log.d(TAG, "inserted " + values.toString() + " rowID = " + rowID);
		                break;
	            }
	        }

	        if (notify) {
	                        
	            getContext().getContentResolver().notifyChange(uri, null);
	        }

	        return result;
	}

	

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		
		 SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		 
		    
		    int uriType = sURIMatcher.match(uri);
		    switch (uriType) {
		    case URL_CONSTITUENCY:
		    	queryBuilder.setTables(SurveyDatabase.CONSTITUENCY_TABLE);
		         break;
		        
		    case URL_CM_DATA:
		        queryBuilder.setTables(SurveyDatabase.CM_DATA_TABLE);
		        break;
		    
		    case URL_PARTY_DATA:
		    	queryBuilder.setTables(SurveyDatabase.PARTY_DATA_TABLE);
		    	break;

		    case URL_MLA_DATA:
		    	queryBuilder.setTables(SurveyDatabase.MLA_DATA_TABLE);
		    	break;

		    case URL_CM:
		    	queryBuilder.setTables(SurveyDatabase.CM_TABLE);
		    	break;
		    	        
		    case URL_PARTY:
		    	queryBuilder.setTables(SurveyDatabase.PARTY_TABLE);
		    	break;
		    
		    case URL_MLA:
		    	queryBuilder.setTables(SurveyDatabase.MLA_TABLE);
		        break; 	
		    
		    default:
		        throw new IllegalArgumentException("Unknown URI");
		    }
		    
		    
		 Cursor cursor = queryBuilder.query(mSurveyDb,projection, selection, selectionArgs, null, null, sortOrder);
		    
		    cursor.setNotificationUri(getContext().getContentResolver(), uri);
		    return cursor;

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count = 0;

        SQLiteDatabase db = mSurveyDb;
        int match = sURIMatcher.match(uri);
        switch (match)
        {
            case URL_CONSTITUENCY:
            {
                count = db.update(SurveyDatabase.CONSTITUENCY_TABLE, values, selection, selectionArgs);
                break;
            }
            
            case URL_CM_DATA:
            {
                count = db.update(SurveyDatabase.CM_DATA_TABLE, values, selection, selectionArgs);
                Log.d(TAG, "updated " + values.toString());
                break;
            }
            
            case URL_PARTY_DATA:
            {
                count = db.update(SurveyDatabase.PARTY_DATA_TABLE, values, selection, selectionArgs);
                Log.d(TAG, "updated " + values.toString());
                break;
            }
            
            case URL_MLA_DATA:
            {
                count = db.update(SurveyDatabase.MLA_DATA_TABLE, values, selection, selectionArgs);
                Log.d(TAG, "updated " + values.toString());
                break;
            }
            
            case URL_CM:
            {
                count = db.update(SurveyDatabase.CM_TABLE, values, selection, selectionArgs);
                Log.d(TAG, "updated " + values.toString());
                break;
            }
            
            case URL_PARTY:
            {
                count = db.update(SurveyDatabase.PARTY_TABLE, values, selection, selectionArgs);
                Log.d(TAG, "updated " + values.toString());
                break;
            }
            
            case URL_MLA:
            {
                count = db.update(SurveyDatabase.MLA_TABLE, values, selection, selectionArgs);
                Log.d(TAG, "updated " + values.toString());
                break;
            }
            
            
            
            default: {
                throw new UnsupportedOperationException("Cannot update that URL: " + uri);
            }
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
	}
    
    
	private void log(String msg)
	{
		Log.d(TAG, msg);
	}
	
	
}
