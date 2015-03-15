package com.achyuthnfn.cnbfinal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper
{
	private final Context myContext;
	private static final String DATABASE_NAME="myworddatabase2";
	private static final String DATABASE_PATH="/data/data/com.achyuthnfn.cnbfinal/databases/";
	private static final int DATABASE_VERSION=1;
	
	public DatabaseAdapter(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		this.myContext=context;
	}
	
	private boolean checkDatabase()
	{
		boolean isExists=false;
		try
		{
			String myPath=DATABASE_PATH+DATABASE_NAME;
			File dbFile=new File(myPath);
			isExists=dbFile.exists();
		}catch(SQLiteException e){}
		return isExists;
	}
	
	public void createDatabase() 
	{
		boolean dbExist = checkDatabase();

        if(dbExist)
        {
              Log.v("DB Exists", "db exists");
        }
       
        boolean dbExist1 = checkDatabase();
        if(!dbExist1)
        {
              this.getReadableDatabase();
              try
              {
            	  copyDatabase();  
            	  //this.close();    
              }
              catch (IOException e)
              {
                    e.printStackTrace();
              }
        }
	}
	
	 public void copyDatabase() throws IOException
	 	{
	    	String outFileName = DATABASE_PATH + DATABASE_NAME;

	    	// copy database into local folder if not present already
	        OutputStream myOutput = new FileOutputStream(outFileName);
	        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = myInput.read(buffer)) > 0)
	        {
	              myOutput.write(buffer, 0, length);
	        }
	        myInput.close();
	        myOutput.flush();
	        myOutput.close();
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
