package com.example.electionsurvey.providers;

import android.net.Uri;

public class Survey {

	
	public static class Constituency
	{
		public static Uri CONTENT_URI = Uri.parse("content://com.example.electionsurvey/constituency") ;
		
		public static String _ID = "_id";
		public static String AREA_HINDI = "area_hindi";
		public static String AREA_ENGLISH = "area_english";
		public static String MLA_HINDI = "mla_hindi";
		public static String MLA_ENGLISH = "mla_english";
		public static String PARTY_HINDI = "party_hindi";
		public static String PARTY_ENGLISH = "party_english";
		
		
	}
	
	
	public static class CMData
	{
		
		public static Uri CONTENT_URI = Uri.parse("content://com.example.electionsurvey/cmdata") ;
		
		public static String _ID = "_id";
		public static String CM_HINDI = "cm_hindi";
		
	}
	
	public static class PartyData
	{
		
		public static Uri CONTENT_URI = Uri.parse("content://com.example.electionsurvey/partydata") ;
		
		public static String _ID = "_id";
		public static String PARTY_HINDI = "party_hindi";
		
	}
	
	
	public static class MlaData
	{
		
		public static Uri CONTENT_URI = Uri.parse("content://com.example.electionsurvey/mladata") ;
		
		public static String _ID = "_id";
		public static String MLA_HINDI = "mla_hindi";
		public static String AREA_HINDI = "area_hindi";
	}
	
	
	public static class CM
	{
		
		public static Uri CONTENT_URI = Uri.parse("content://com.example.electionsurvey/cm") ;
		
		public static String _ID = "_id";
		public static String CM_HINDI = "cm_hindi";
		public static String AREA_HINDI = "area_hindi";
		public static String AREA_2 = "area_2";
		public static String VOTES = "votes";
		
	}
	
	
	public static class Party
	{
		
		public static Uri CONTENT_URI = Uri.parse("content://com.example.electionsurvey/party") ;
		
		public static String _ID = "_id";
		public static String PARTY_HINDI = "party_hindi";
		public static String AREA_HINDI = "area_hindi";
		public static String AREA_2 = "area_2";
		public static String VOTES = "votes";
		
	}
	
	
	public static class Mla
	{
		
		public static Uri CONTENT_URI = Uri.parse("content://com.example.electionsurvey/mla") ;
		
		public static String _ID = "_id";
		public static String MLA_HINDI = "mla_hindi";
		public static String AREA_HINDI = "area_hindi";
		public static String AREA_2 = "area_2";
		public static String VOTES = "votes";
		
	}
	
	
	
	
}
