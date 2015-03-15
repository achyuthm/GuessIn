package com.achyuthnfn.cnbfinal;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RulesListAdapter extends ArrayAdapter<String>
{
	private final Context context;
	private final String[] rules;
	View rowView;
	Typeface typeface;
	
	public RulesListAdapter(Context context, String[] rules, String font)
	{
		super(context, R.layout.rules_list, rules);
		// TODO Auto-generated constructor stub
		 this.context = context;
		 this.rules = rules;
		 typeface=Typeface.createFromAsset(context.getAssets(), font);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowView = inflater.inflate(R.layout.rules_list, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.rule_desc);
		textView.setTypeface(typeface);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.rule_indicator);
		textView.setText(rules[position]);
		String s=rules[position];
		if(s.equals("Right letters in the right place"))
		{
			imageView.setImageResource(R.drawable.icon_green);
		}
		else if(s.equals("Right letters in the wrong place"))
		{
			imageView.setImageResource(R.drawable.icon_yellow);
		}
		else
		{
			imageView.setImageResource(R.drawable.icon_red);
		}
		return rowView;
	}
	
	
}
