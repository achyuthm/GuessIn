package com.achyuthnfn.cnbfinal;

// class to fetch a random word from the stored database, invoked just before game play, 
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FetchWord extends SQLiteOpenHelper
{

	private SQLiteDatabase wordDatabase;
	@SuppressWarnings("unused")
	private final Context myContext;
	private static final String DATABASE_NAME="myworddatabase2";
	private static final String DATABASE_PATH="/data/data/com.achyuthnfn.cnbfinal/databases/";
	private static final int DATABASE_VERSION=1;
	private static final String COLUMN_NAME="words";
	
	public FetchWord(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext=context;
		// TODO Auto-generated constructor stub
	}
	
	public void openDatabase() throws SQLException
    {
		//open database, check already made in main activity
		String myPath = DATABASE_PATH + DATABASE_NAME;
        wordDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
	
	public synchronized void closeDataBase()throws SQLException
    {
          if(wordDatabase != null)
                wordDatabase.close();
          super.close();
    }
	
	// get one random word of given string length
	public String getRandomWord(String numChars) 
    {
    	String sqlQuery = "";
    	String randomWord="";
    	sqlQuery="SELECT * FROM mywordlist WHERE "+COLUMN_NAME+" like '"+numChars+"' ORDER BY RANDOM() LIMIT 1";
	    Cursor cursor = wordDatabase.rawQuery(sqlQuery, null);
	
	    	if(cursor.moveToFirst())
	    	{
	    		while(!cursor.isAfterLast())
	    		{
	    			randomWord=cursor.getString(1);
	    			cursor.moveToNext();
	    		}
	    	}
    	return randomWord;
    }
	
	public void insertScore(String word, int attempts)
	{
		String sqlQuery="INSERT INTO bestscores (word, attempts) VALUES ('"+word+"','"+attempts+"')";
		wordDatabase.execSQL(sqlQuery);
	}
	public Cursor getBestScore()
	{
		String sqlQuery="SELECT word, attempts FROM bestscores ORDER BY attempts ASC LIMIT 5";
		Cursor cursor=wordDatabase.rawQuery(sqlQuery, null);
		return cursor;
	}
	
	public void clearScores()
	{
		String sqlQuery="delete from bestscores";
		wordDatabase.execSQL(sqlQuery);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}
	
	
}
