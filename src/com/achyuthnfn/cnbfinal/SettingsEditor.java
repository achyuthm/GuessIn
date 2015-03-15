package com.achyuthnfn.cnbfinal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
public class SettingsEditor 
{
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	Context context;
	
	
	public SettingsEditor(Context context) 
	{
		// TODO Auto-generated constructor stub
		this.context=context;
		sharedPreferences=context.getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
		editor=sharedPreferences.edit();
	}
	
	public void setGameMode(int gameMode)
	{
		editor.putInt("GAMEMODE", gameMode).commit();
	}
	
	public void setSound(Boolean soundON)
	{
		editor.putBoolean("SOUNDON", soundON).commit();
	}
	
	public int getGameMode()
	{
		return sharedPreferences.getInt("GAMEMODE", 0);
	}
	
	public Boolean getSoundPref()
	{
		return sharedPreferences.getBoolean("SOUNDON", true);
	}
	
	public void setGamesCount()
	{
		int gameCount=sharedPreferences.getInt("GAMECOUNT", 0);
		editor.putInt("GAMECOUNT", ++gameCount).commit();
	}
	
	public void setLettersCount(int numLetters)
	{
		int curLetterCount=sharedPreferences.getInt("LETTERCOUNT", 0);
		curLetterCount+=numLetters;
		editor.putInt("LETTERCOUNT", curLetterCount).commit();
	}
	
	public void setWordCount()
	{
		int curWordCount=sharedPreferences.getInt("WORDCOUNT", 0);
		editor.putInt("WORDCOUNT", ++curWordCount).commit();
	}
	
	public int getGamesCount()
	{
		return sharedPreferences.getInt("GAMECOUNT", 0);
	}
	
	public int getLettersCount()
	{
		return sharedPreferences.getInt("LETTERCOUNT", 0);
	}
	
	public int getWordCount()
	{
		return sharedPreferences.getInt("WORDCOUNT", 0);
	}
	
	public void clearStats()
	{
		editor.clear().commit();
	}
}
