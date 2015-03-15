package com.achyuthnfn.cnbfinal;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.plus.PlusShare;

public class Glory extends Activity 
{
	String attempts,word;
	TextView gloryMessage,shareIcon,newGameicon,newGameText,awesomeness,starSymbols;
	TextView fbIcon,twitterIcon,gPlusIcon,mailIcon;
	Cursor cursor;
	ListView bestScoreList;
	BestScoreListAdapter bestScoreListAdapter;
	List<BestScore> bestScore;
	FetchWord fetchWord;
	String bestWord;
	int bestAttempt;
	TextView glory,stats;
	BestScore bScore;
	LinearLayout newGameButton,shareButton;
	RelativeLayout menuIcon;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_glory);
		Bundle extras=getIntent().getExtras();
		final Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
		
		final Typeface fontproxima=(Typeface.createFromAsset(getAssets(), "ProximaNova-Regular.otf"));
		
		awesomeness=(TextView)findViewById(R.id.status_text);
		awesomeness.setTypeface(fontproxima);
		
		stats=(TextView)findViewById(R.id.stats);
		stats.setTypeface(fontproxima);
		
		TextView menuIconText=(TextView)findViewById(R.id.menuicontext);
		menuIconText.setTypeface(font);
		
		TextView gloryMessage=(TextView)findViewById(R.id.glory_card);
		gloryMessage.setTypeface(fontproxima);
		
		starSymbols=(TextView)findViewById(R.id.star_symbol);
		starSymbols.setTypeface(font);
		
		
		newGameText=(TextView)findViewById(R.id.newgame_text);
		TextView shareText=(TextView)findViewById(R.id.share_text);
		newGameText.setTypeface(fontproxima);
		shareText.setTypeface(fontproxima);
		
		
		shareIcon=(TextView)findViewById(R.id.share_icon);
		newGameicon=(TextView)findViewById(R.id.newgame_icon);
		shareIcon.setTypeface(font);
		newGameicon.setTypeface(font);
		attempts=extras.getString("ATTEMPTS");
		word=extras.getString("WORD");
		bestScore=new ArrayList<BestScore>();
		fetchWord=new FetchWord(this);
		gloryMessage=(TextView)findViewById(R.id.glory_card);
		
		bestScoreList=(ListView)findViewById(R.id.best_score_list);
		bestScoreList.setAdapter(bestScoreListAdapter);
		
		if(Integer.parseInt(attempts)==1)
			{gloryMessage.setText("You guessed the\nword '"+word+"'\nin "+attempts+" attempt!");}
		else
			{gloryMessage.setText("You guessed the\nword '"+word+"'\nin "+attempts+" attempts!");}
		
		newGameButton=(LinearLayout)findViewById(R.id.newgamelayout);
		newGameButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Glory.this,GamePlay.class);
				startActivity(intent);
				finish();
			}
		});
		
		
		shareButton=(LinearLayout)findViewById(R.id.sharelayout);
		shareButton.setOnClickListener(new OnClickListener() 
		{	
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Dialog dialog=new Dialog(Glory.this);
				dialog.setContentView(R.layout.share_dialog_layout);
				dialog.setTitle("Share Via");
				
				
				fbIcon=(TextView)dialog.findViewById(R.id.fb_icon);
				twitterIcon=(TextView)dialog.findViewById(R.id.twitter_icon);
				gPlusIcon=(TextView)dialog.findViewById(R.id.gplus_icon);
				
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
								 	 "caption=GuessIn&" +
								 	 "description="+Uri.parse("My smartphone just thought of a word and I guessed it in "+attempts+" attempts. Check out the new game by @nfnlabs called GuessIn!")+
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
						String url = "https://twitter.com/intent/tweet?text=My smartphone just thought of a word and I guessed it in "+attempts+" attempts. Check out Guessin by @nfnlabs:&url="+Uri.parse("http://goo.gl/CGmGEx");
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
						Intent intent= new PlusShare.Builder(Glory.this)
							.setText("My smartphone just thought of a word and I guessed it in "+attempts+" attempts. Check out the new game by @nfnlabs called GuessIn!")
							.setType("text/plain")
							.setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.achyuthnfn.cnbfinal"))
							.getIntent();
						startActivityForResult(intent, 0);
						
					}
				});
				
				dialog.show();
			}
		});
		
		
		menuIcon=(RelativeLayout)findViewById(R.id.menuicon);
		menuIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Glory.this,SettingsActivity.class);
				intent.putExtra("GAMESTARTED", false);
				startActivity(intent);
			}
		});
		
		
		fetchWord.openDatabase();
		cursor=fetchWord.getBestScore();
		if(cursor.moveToFirst())
    	{
    		while(!cursor.isAfterLast())
    		{
    			bScore=new BestScore();
    			bestWord=cursor.getString(0);
    			bestAttempt=cursor.getInt(1);
    			bScore.setValues(bestWord, bestAttempt);
    			bestScore.add(bScore);
    			cursor.moveToNext();
    			
    		}
    	}
		cursor.close();
		bestScoreListAdapter=new BestScoreListAdapter(this, bestScore, font);
		bestScoreList.setAdapter(bestScoreListAdapter);
		fetchWord.closeDataBase();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glory, menu);
		return true;
	}
	
	


}
