package com.achyuthnfn.cnbfinal;

// Adapter for list view where all the users attempts are displayed on game play Activity

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class AttemptsListAdapter extends ArrayAdapter<UserValues>
{
	private final Context context;
	View rowView;
	TextView attemptWord;
	TextView yellowIcon;
	TextView greenOrRedIcon;
	List<UserValues> attempts;
	CowsAndBulls cnb;
	Typeface typeface;
	
	
	public AttemptsListAdapter(Context context,List<UserValues> values,String font) {
		super(context, R.layout.attempts_list,values);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.attempts = values;
		typeface=Typeface.createFromAsset(context.getAssets(), font);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.attempts_list, parent, false);
		attemptWord = (TextView) rowView.findViewById(R.id.attempt_word);
		attemptWord.setTypeface(typeface);
		yellowIcon = (TextView) rowView.findViewById(R.id.yellow_icon);
		yellowIcon.setTypeface(typeface);
		greenOrRedIcon = (TextView) rowView.findViewById(R.id.green_red_icon);
		greenOrRedIcon.setTypeface(typeface);
		String userString=attempts.get(position).getword().toString();
		String cow=Integer.toString(attempts.get(position).getcow());
		String bull=Integer.toString(attempts.get(position).getbull());
		
		
		if(Integer.parseInt(bull)!=0 && Integer.parseInt(cow)== 0 )
		{
			if(Integer.parseInt(bull)==userString.length()) // win condition, word guessed correctly
			{	
				greenOrRedIcon.setText(bull);
				greenOrRedIcon.setBackgroundResource(R.drawable.icon_green);
			}
			else // only green
			{
				greenOrRedIcon.setText(bull);
				greenOrRedIcon.setBackgroundResource(R.drawable.icon_green);
			}
		}
		else if(Integer.parseInt(cow)>0 && Integer.parseInt(bull)<userString.length() && Integer.parseInt(bull)>0)
		{
			// green and yellow both
			greenOrRedIcon.setText(bull);
			greenOrRedIcon.setBackgroundResource(R.drawable.icon_green);
			yellowIcon.setText(cow);
			yellowIcon.setBackgroundResource(R.drawable.icon_yellow);
		}
		else if(Integer.parseInt(bull)==0 && Integer.parseInt(cow)>0)
		{
			// partial condition
			yellowIcon.setText("");
			greenOrRedIcon.setText(cow);
			greenOrRedIcon.setBackgroundResource(R.drawable.icon_yellow);
		}
		else if(Integer.parseInt(bull)==0 && Integer.parseInt(cow)==0)
		{
			//all letters wrong
			
			greenOrRedIcon.setText("");
			greenOrRedIcon.setBackgroundResource(R.drawable.icon_red);
		}
		attemptWord.setText(userString);
		
		return rowView;
	}
	

	
}
