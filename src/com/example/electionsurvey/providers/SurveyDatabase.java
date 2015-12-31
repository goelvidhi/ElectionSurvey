package com.example.electionsurvey.providers;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;



public class SurveyDatabase extends SQLiteOpenHelper{
	
    private static final String TAG = "SurveyDatabase";
    
    private static final String DATABASE_NAME = "electionsurvey.db";

	private static final int DATABASE_VERSION = 1;
	    
	 public static final String CONSTITUENCY_TABLE = "constituency";
	 public static final String CM_DATA_TABLE = "cm_data";
	 public static final String PARTY_DATA_TABLE = "party_data";
	 public static final String MLA_DATA_TABLE = "mla_data";
	 
	 public static final String MLA_TABLE = "mla";
	 public static final String PARTY_TABLE = "party";
	 public static final String CM_TABLE = "cm";

	    
        // Context to access resources with
        private Context mContext;

        
        /**
         * DatabaseHelper helper class for loading apns into a database.
         *
         * @param parser the system-default parser for apns.xml
         * @param confidential an optional parser for confidential APNS (stored separately)
         */
        public SurveyDatabase(Context context) {
        	
           super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
            
            log("SurveyDatabase constructor");
        }
        
        
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		log("onCreate");
		
		db.execSQL("CREATE TABLE " + CONSTITUENCY_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, area_hindi TEXT, area_english TEXT, mla_hindi TEXT, mla_english TEXT, party_hindi TEXT, party_english TEXT);");
		
		db.execSQL("CREATE TABLE " + CM_DATA_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, cm_hindi TEXT);");
		
		db.execSQL("CREATE TABLE " + PARTY_DATA_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "party_hindi TEXT);");
		
		db.execSQL("CREATE TABLE " + MLA_DATA_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "mla_hindi TEXT, area_hindi TEXT);");
		
		db.execSQL("CREATE TABLE " + CM_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "cm_hindi TEXT, area_hindi TEXT, area_2 TEXT, votes INTEGER);");
		
		db.execSQL("CREATE TABLE " + PARTY_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "party_hindi TEXT, area_hindi TEXT, area_2 TEXT, votes INTEGER);");
		
		db.execSQL("CREATE TABLE " + MLA_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, mla_hindi TEXT, area_hindi TEXT, area_2 TEXT, votes INTEGER);");

	    initDatabase(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		log("onUpgrade");
		 db.execSQL("DROP TABLE IF EXISTS " + CONSTITUENCY_TABLE);
		 
		 db.execSQL("DROP TABLE IF EXISTS " + CM_DATA_TABLE);
		 db.execSQL("DROP TABLE IF EXISTS " + PARTY_DATA_TABLE);
		 db.execSQL("DROP TABLE IF EXISTS " + MLA_DATA_TABLE);
		 
		 db.execSQL("DROP TABLE IF EXISTS " + MLA_TABLE);
		 db.execSQL("DROP TABLE IF EXISTS " + PARTY_TABLE);
		 db.execSQL("DROP TABLE IF EXISTS " + CM_TABLE);
		 
		  onCreate(db);
	}
	
	
	private void initDatabase(SQLiteDatabase db) {
              	
			log("initDatabase");
            loadConstituency(db);
            loadCM(db);
            loadParty(db);
           }
	
	
    /*
     * Loads constituencies from xml file into the database
     *
     * @param db the sqlite database to write to
     * @param parser the xml parser
     *
     */
    private void loadConstituency(SQLiteDatabase db) {
    	
    	log("loadConstituency");
       
    	// Read mlaprofiles data
        Resources r = mContext.getResources();
        XmlResourceParser parser = r.getXml(com.example.electionsurvey.R.xml.mlaprofiles);
      int version = -1;
        
    	
            try {
            	
            	 XmlUtils.beginDocument(parser, "constituencys");
                 version = Integer.parseInt(parser.getAttributeValue(null, "version"));
                
            	while(true)
            	{
            	XmlUtils.nextElement(parser);
            	ContentValues row = getConstituencyRow(parser);
            	
            	if(row != null) insertIntoDb(db,CONSTITUENCY_TABLE,row);
            	else break;
                 
            	}       
            } catch (XmlPullParserException e)  {
                Log.e(TAG, "Got execption while getting perferred time zone.", e);
            } catch (IOException e) {
                Log.e(TAG, "Got execption while getting perferred time zone.", e);
            }
            finally{
            	parser.close();
            }
        }
    
    
   private ContentValues getConstituencyRow(XmlPullParser parser)
    {
    	log("getConstituencyRow");
    	if(! "constituency".equals(parser.getName()))
    		return null;
    	
    	ContentValues vals = new ContentValues();
    	
    	String area_hindi = parser.getAttributeValue(null, "area_hindi");
    	String area_english = parser.getAttributeValue(null, "area_english"); 
    	String mla_hindi = parser.getAttributeValue(null, "mla_hindi");
    	String mla_english = parser.getAttributeValue(null, "mla_english");
    	String party_hindi = parser.getAttributeValue(null, "party_hindi");
    	String party_english = parser.getAttributeValue(null, "party_english");
    	
    	
    	if(area_hindi != null)
    	vals.put("area_hindi", area_hindi);
    	
    	if(area_english != null)
    	vals.put("area_english", area_english);
    	
    	if (mla_hindi != null)
    		vals.put("mla_hindi", mla_hindi);
    	
    	if(mla_english != null)
    		vals.put("mla_english", mla_english);
    	
    	if(party_hindi != null)
    		vals.put("party_hindi", party_hindi);
    	
    	if(party_english != null)
    		vals.put("party_english", party_english);
    	
    	
    	//log("Area_english =" + area_english);
    	
    	
    	return vals;
    }
    
    
    
    /*
     * Loads cm from xml file into the database
     *
     * @param db the sqlite database to write to
     * @param parser the xml parser
     *
     */
    private void loadCM(SQLiteDatabase db) {
       
    	log("loadCM");
    	// Read cm data
        Resources r = mContext.getResources();
        XmlResourceParser parser = r.getXml(com.example.electionsurvey.R.xml.cm);
      int version = -1;
        
    	
            try {
            	
            	 XmlUtils.beginDocument(parser, "cms");
                 version = Integer.parseInt(parser.getAttributeValue(null, "version"));
                
            	while(true)
            	{
            	XmlUtils.nextElement(parser);
            	ContentValues row = getCMRow(parser);
            	
            	if(row != null) insertIntoDb(db,CM_DATA_TABLE,row);
            	else break;
                 
            	}       
            } catch (XmlPullParserException e)  {
                Log.e(TAG, "Got execption while getting perferred time zone.", e);
            } catch (IOException e) {
                Log.e(TAG, "Got execption while getting perferred time zone.", e);
            }
            finally{
            	parser.close();
            }
        }
    
    
    private ContentValues getCMRow (XmlPullParser parser)
    {
    	
    	log("getCMRow");
    	
    	if(! "cm".equals(parser.getName()))
    		return null;
    	
    	ContentValues vals = new ContentValues();
    	
    	String cm_hindi = parser.getAttributeValue(null, "cm_hindi");
    	
    	
    	if(cm_hindi != null)
    	vals.put("cm_hindi", cm_hindi);
    	
    	log("cm_hindi =" + cm_hindi);
    	   	
    	return vals;
    }
    
    
    /*
     * Loads party from xml file into the database
     *
     * @param db the sqlite database to write to
     * @param parser the xml parser
     *
     */
    private void loadParty(SQLiteDatabase db) {
      
    	log("loadParty");
    	
    	// Read cm data
        Resources r = mContext.getResources();
        XmlResourceParser parser = r.getXml(com.example.electionsurvey.R.xml.party);
      int version = -1;
        
    	
            try {
            	
            	 XmlUtils.beginDocument(parser, "partys");
                 version = Integer.parseInt(parser.getAttributeValue(null, "version"));
                
            	while(true)
            	{
            	XmlUtils.nextElement(parser);
            	ContentValues row = getPartyRow(parser);
            	
            	if(row != null) insertIntoDb(db,PARTY_DATA_TABLE,row);
            	else break;
                 
            	}       
            } catch (XmlPullParserException e)  {
                Log.e(TAG, "Got execption while getting perferred time zone.", e);
            } catch (IOException e) {
                Log.e(TAG, "Got execption while getting perferred time zone.", e);
            }
            finally{
            	parser.close();
            }
        }
    
    
   private ContentValues getPartyRow(XmlPullParser parser)
    {
    	
	   log("getPartyRow");
	   
    	if(! "party".equals(parser.getName()))
    		return null;
    	
    	ContentValues vals = new ContentValues();
    	
    	String party_hindi = parser.getAttributeValue(null, "party_hindi");
    	
    	
    	if(party_hindi != null)
    	vals.put("party_hindi", party_hindi);
    	   	
    	log("party =" + party_hindi);
    	return vals;
    }
    
       
    private void insertIntoDb(SQLiteDatabase db, String table,ContentValues row) 
    {
    	// TODO Auto-generated method stub
    	
    	log("insertIntoDb");
    	db.insert(table, null, row);
		
	}
    
    private void log(String msg)
    {
    	Log.d(TAG, msg);
    }
}


