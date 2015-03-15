package com.achyuthnfn.cnbfinal;

public class UserValues
{
	public String userWord = "";
	public int bull = 0,cow= 0;
	
	public UserValues() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void setword(String getstr)
	{
		this.userWord = getstr;
	}
	
	public String getword()
	{
		return userWord;
	}
	
	public void setcow(int cow)
	{
		this.cow = cow;
	}
	
	public int getcow() 
	{
		return this.cow;
	}
	
	public void setbull(int bull)
	{
		this.bull = bull;
	}
	
	public int getbull() 
	{
		return this.bull;
	}
	
}
