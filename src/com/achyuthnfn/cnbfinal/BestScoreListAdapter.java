package com.achyuthnfn.cnbfinal;
// Adapter for list view for showing best scores, in Glory Activity
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BestScoreListAdapter extends ArrayAdapter<BestScore>
{
	Context context;
	List<BestScore> bestScore;
	TextView word;
	TextView attemptCount;
	Typeface font;
	public BestScoreListAdapter(Context context,List<BestScore> values, Typeface tf) 
	{
		super(context, R.layout.best_scores,values);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.bestScore = values;
		font=tf;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		// sets word and corresponding number of attempts 
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.best_scores, parent, false);
		word=(TextView)rowView.findViewById(R.id.best_word);
		word.setTypeface(font);
		attemptCount=(TextView)rowView.findViewById(R.id.best_attempt);
		attemptCount.setTypeface(font);
		word.setText(bestScore.get(position).getWord());
		attemptCount.setText(Integer.toString(bestScore.get(position).getAttemptCount()));
		return rowView;
	}
}
