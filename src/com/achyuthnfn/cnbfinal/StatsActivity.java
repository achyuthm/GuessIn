package com.achyuthnfn.cnbfinal;


import com.google.android.gms.plus.PlusShare;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class StatsActivity extends Activity {

	SettingsEditor settingsEditor;
	TextView letterCountText,wordCountText,gameCountText,statsText;
	TextView lCount,wCount,gCount,share;
	CounterText letterCounterText,wordCounterText,gameCounterText;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		settingsEditor=new SettingsEditor(this);
		
		final int letterCount=settingsEditor.getLettersCount();
		final int wordsCount=settingsEditor.getWordCount();
		final int gamesCount=settingsEditor.getGamesCount();
		
		Typeface fontBold=Typeface.createFromAsset(getAssets(), "ProximaNova-Bold.otf");
		Typeface fontRegular=Typeface.createFromAsset(getAssets(), "ProximaNova-Regular.otf");
		final Typeface font=Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
		
		
		letterCountText=(TextView)findViewById(R.id.letter_text);
		wordCountText=(TextView)findViewById(R.id.word_text);
		gameCountText=(TextView)findViewById(R.id.game_text);
		
		lCount=(TextView)findViewById(R.id.letter_count);
		wCount=(TextView)findViewById(R.id.word_count);
		gCount=(TextView)findViewById(R.id.game_count);
		
		lCount.setTypeface(fontBold);
		wCount.setTypeface(fontBold);
		gCount.setTypeface(fontBold);
		
		letterCounterText=new CounterText(0, letterCount, 1500, lCount, getApplicationContext());
		wordCounterText=new CounterText(0, wordsCount, 1500, wCount, getApplicationContext());
		gameCounterText=new CounterText(0, gamesCount, 1500, gCount, getApplicationContext());
		
		letterCounterText.counterTextThread();
		wordCounterText.counterTextThread();
		gameCounterText.counterTextThread();
		
		letterCountText.setTypeface(fontRegular);
		wordCountText.setTypeface(fontRegular);
		gameCountText.setTypeface(fontRegular);
		
		
		share=(TextView)findViewById(R.id.share_with_friends);
		share.setTypeface(fontBold);
		share.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				
				
				Dialog dialog=new Dialog(StatsActivity.this);
				dialog.setContentView(R.layout.share_dialog_layout);
				dialog.setTitle("Share Via");
				
				
				TextView fbIcon=(TextView)dialog.findViewById(R.id.fb_icon);
				TextView twitterIcon=(TextView)dialog.findViewById(R.id.twitter_icon);
				TextView gPlusIcon=(TextView)dialog.findViewById(R.id.gplus_icon);
				
				fbIcon.setTypeface(font);
				twitterIcon.setTypeface(font);
				gPlusIcon.setTypeface(font);
			
				
				fbIcon.setOnClickListener(new OnClickListener() 
				{	
					@Override
					public void onClick(View v) 
					{
						// TODO Auto-generated method stub
						String url = "https://www.facebook.com/dialog/feed?app_id=1507764116109075&" +
								 "link=https://play.google.com/store/apps/details?id=com.achyuthnfn.cnbfinal&" +
								 "caption=GuessIn&"+
								 "description="+Uri.parse("I have played "+gamesCount+" games, guessed "+wordsCount+" words and entered "+letterCount+" letters via GuessIn. Check out the new game by @nfnlabs") +
								 "&redirect_uri=https://www.facebook.com/connect/login_success.html"+
								 "&picture=http://nfnlabs.in/wp-content/uploads/2014/06/Share%20Image.png";
						Intent intent=new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse(url));
						startActivity(intent);
					}
				});
				
				twitterIcon.setOnClickListener(new OnClickListener() 
				{	
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String url = "https://twitter.com/intent/tweet?text=I have played "+gamesCount+" games, guessed "+wordsCount+" words and entered "+letterCount+" letters via GuessIn. Check out the new game by @nfnlabs: &url="+Uri.parse("http://goo.gl/CGmGEx");
						Intent intent=new Intent(Intent.ACTION_VIEW);
						Uri uri=Uri.parse(url);
						intent.setData(uri);
						startActivity(intent);
					}
				});
				
				gPlusIcon.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent= new PlusShare.Builder(StatsActivity.this)
							.setText("I have played "+gamesCount+" games, guessed "+wordsCount+" words and entered "+letterCount+" letters via GuessIn. Check out the new game by @nfnlabs")
							.setType("text/plain")
							.setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.achyuthnfn.cnbfinal"))
							.getIntent();
						startActivityForResult(intent, 0);
						
					}
				});
				
				dialog.show();
			}
		});
		
	}
}	

