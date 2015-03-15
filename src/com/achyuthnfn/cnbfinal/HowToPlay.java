package com.achyuthnfn.cnbfinal;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class HowToPlay extends Activity {

	TextView statusText,headerText,instruction1,instruction2,instruction3,instruction4,footer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_how_to_play);
		
		Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
		
		statusText=(TextView)findViewById(R.id.status_text);
		headerText=(TextView)findViewById(R.id.heading1);
		instruction1=(TextView)findViewById(R.id.instr1);
		instruction2=(TextView)findViewById(R.id.instr2);
		instruction3=(TextView)findViewById(R.id.instr3);
		instruction4=(TextView)findViewById(R.id.instr4);
		footer=(TextView)findViewById(R.id.footer_txt);
		
		statusText.setTypeface(font);
		headerText.setTypeface(font);
		instruction1.setTypeface(font);
		instruction2.setTypeface(font);
		instruction3.setTypeface(font);
		instruction4.setTypeface(font);
		footer.setTypeface(font);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.how_to_play, menu);
		return true;
	}

}
