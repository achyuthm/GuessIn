package com.achyuthnfn.cnbfinal;

// Class for incrementing stats values form 0 to n on Stats Activity
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

public class CounterText 
{
	TextView view;
	int startValue,endValue,difference,interval;
	Context context;
	CountDownTimer timer;
	
	public CounterText(int startValue, int endValue, int interval, TextView view, Context context) 
	{
		// TODO Auto-generated constructor stub
		// get values from database on total entries by user
		this.startValue=startValue;
		this.endValue=endValue;
		this.interval=interval;
		this.view=view;
		this.context=context;
	}
	
	public void counterTextThread()
	{
		int difference=(endValue-startValue);
		if(difference==0)
		{
			view.setText("0");
		}
		else
		{
			timer= new CountDownTimer(interval,(interval/difference)) //set speed according to current values of different fields
			{
				int start=0;
				@Override
				public void onTick(long millisUntilFinished) 
				{
					// TODO Auto-generated method stub
					view.setText(Integer.toString(start));
					start++;
				}
				
				@Override
				public void onFinish() 
				{
					// TODO Auto-generated method stub
					view.setText(Integer.toString(endValue));
				}
			};
			timer.start();
		}
	}
}
