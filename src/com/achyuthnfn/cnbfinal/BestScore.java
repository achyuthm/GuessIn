package com.achyuthnfn.cnbfinal;

public class BestScore 
{
	String word;
	int numAttempt;
	public void setValues(String word, int numAttempts)
	{
		this.word=word;
		this.numAttempt=numAttempts;
	}
	public String getWord()
	{
		return word;
	}
	public int getAttemptCount()
	{
		return numAttempt;
	}
}
