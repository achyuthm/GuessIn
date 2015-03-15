package com.achyuthnfn.cnbfinal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.plus.PlusShare;

public class SettingsActivity extends Activity  {

	TextView rightIcon1,rightIcon2,rightIcon3,rightIcon4,soundIndicatorOn,heart;
	TextView modeText,soundOption,howTo,tellFriend,stats,about;
	TextView fbIcon,gPlusIcon,twitterIcon,share;
	TextView designCredits,developerCredits,nfnLabs,feedBackText;
	RelativeLayout soundMenu,statMenu,tellAFreind,howToPlay,feedBackOptions;
	Boolean soundOn=true,modeChanged,gameInProgress;
	RadioGroup radioGroup;
	SettingsEditor settingsEditor;
	RadioButton easy,medium,complex;
	int curGameMode;
	Bundle bundle;
	static TelephonyManager manager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		manager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		
		final Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
		Typeface fontProximaRegular=Typeface.createFromAsset(getAssets(), "ProximaNova-Regular.otf");
		
		rightIcon1=(TextView)findViewById(R.id.right_icon1);
		rightIcon2=(TextView)findViewById(R.id.right_icon2);
		rightIcon3=(TextView)findViewById(R.id.right_icon3);
		rightIcon4=(TextView)findViewById(R.id.right_icon4);
		rightIcon1.setTypeface(font);
		rightIcon2.setTypeface(font);
		rightIcon3.setTypeface(font);
		rightIcon4.setTypeface(font);
		
		modeText=(TextView)findViewById(R.id.mode_text);
		soundOption=(TextView)findViewById(R.id.sound_option);
		howTo=(TextView)findViewById(R.id.howtoplay);
		tellFriend=(TextView)findViewById(R.id.tellafriend);
		stats=(TextView)findViewById(R.id.yourstats);
		feedBackText=(TextView)findViewById(R.id.feedback);
		
		modeText.setTypeface(fontProximaRegular);
		soundOption.setTypeface(fontProximaRegular);
		howTo.setTypeface(fontProximaRegular);
		tellFriend.setTypeface(fontProximaRegular);
		stats.setTypeface(fontProximaRegular);
		feedBackText.setTypeface(fontProximaRegular);
		
		heart=(TextView)findViewById(R.id.heart);
		heart.setTypeface(font);
		
		modeChanged=false;
		bundle=getIntent().getExtras();
		gameInProgress=bundle.getBoolean("GAMESTARTED");
		
		settingsEditor=new SettingsEditor(this);
		
		radioGroup=(RadioGroup)findViewById(R.id.modes);
		
		easy=(RadioButton)findViewById(R.id.easymode);
		medium=(RadioButton)findViewById(R.id.mediummode);
		complex=(RadioButton)findViewById(R.id.complexmode);
		
		easy.setTypeface(fontProximaRegular);
		medium.setTypeface(fontProximaRegular);
		complex.setTypeface(fontProximaRegular);
		
		statMenu=(RelativeLayout)findViewById(R.id.stats_option);
		statMenu.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SettingsActivity.this,StatsActivity.class));
			}
		});
		
		feedBackOptions=(RelativeLayout)findViewById(R.id.feedback_option);
		feedBackOptions.setOnClickListener(new OnClickListener() 
		{	
			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				String deviceName=android.os.Build.MODEL;
				String deviceManufacturer=android.os.Build.MANUFACTURER;
				String userCountry=manager.getNetworkCountryIso();
				PackageInfo pInfo=null;
				try {
					pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String version = pInfo.versionName;
				
				
				Intent intent=new Intent(Intent.ACTION_SEND);
				intent.setType("message/rfc822");
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@nfnlabs.in"});
				intent.putExtra(Intent.EXTRA_SUBJECT, "GuessIn FeedBack");
				intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\n\n\n\n\n\n---------------------------------------------------------\nModel: "+deviceName+"\nManufacturer: "+deviceManufacturer+"\nApp Version: "+version+"\nCountry: "+userCountry.toUpperCase());
				startActivity(Intent.createChooser(intent, "Send Email"));
			}
		});
		
		curGameMode=settingsEditor.getGameMode();
		
		View getview = radioGroup.getChildAt(curGameMode);
		radioGroup.check(getview.getId());
		
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int newGameMode=radioGroup.getCheckedRadioButtonId();
				View radioGroupChild = radioGroup.findViewById(newGameMode);
				if(gameInProgress)
				{	
					if(newGameMode!=curGameMode)
					{
						modeChanged=true;
					}
				}
				settingsEditor.setGameMode(radioGroup.indexOfChild(radioGroupChild));
			}
		});
		
		soundIndicatorOn=(TextView)findViewById(R.id.sound_indicator_on);
		soundMenu=(RelativeLayout)findViewById(R.id.sound_menu);
		soundIndicatorOn.setTypeface(font);
		
		
		soundOn = settingsEditor.getSoundPref();

		
		if(!soundOn)
		{
			soundIndicatorOn.setText(getResources().getString(R.string.cross_symbol));
			soundIndicatorOn.setTextColor(Color.parseColor("#d0021b"));
		}
		else
		{
			soundIndicatorOn.setText(getResources().getString(R.string.tick_symbol));
			soundIndicatorOn.setTextColor(Color.parseColor("#7ed321"));
		}
		
		
		soundMenu.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				if(!soundOn)
				{
					soundOn=true;
					soundIndicatorOn.setText(getResources().getString(R.string.tick_symbol));
					soundIndicatorOn.setTextColor(Color.parseColor("#7ed321"));
					settingsEditor.setSound(soundOn);
				}
				else
				{
					soundOn=false;
					soundIndicatorOn.setText(getResources().getString(R.string.cross_symbol));
					soundIndicatorOn.setTextColor(Color.parseColor("#d0021b"));
					settingsEditor.setSound(soundOn);
				}
//				Toast.makeText(getApplicationContext(), ""+settingsEditor.getSoundPref(), 1000).show();
			}
		});
		
		
		howToPlay=(RelativeLayout)findViewById(R.id.option_howtoplay);
		howToPlay.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent=new Intent(SettingsActivity.this,HowToPlay.class);
				startActivity(intent);
			}
		});
		
		tellAFreind=(RelativeLayout)findViewById(R.id.option_tellafriend);
		tellAFreind.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Dialog dialog=new Dialog(SettingsActivity.this);
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
								 "description="+Uri.parse("Check out GuessIn, a cool word game by @nfnlabs where you have to guess the word your smartphone has thought of!") +
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
						String url = "https://twitter.com/intent/tweet?text=Check out GuessIn, a cool word game by @nfnlabs where you have to guess the word your smartphone has thought of!&url="+Uri.parse("http://goo.gl/CGmGEx");
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
						Intent intent= new PlusShare.Builder(SettingsActivity.this)
							.setText("Check out GuessIn, a cool word game by @nfnlabs where you have to guess the word your smartphone has thought of!")
							.setType("text/plain")
							.setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.achyuthnfn.cnbfinal"))
							.getIntent();
						startActivityForResult(intent, 0);
						
					}
				});
				
				dialog.show();
			}
		});
		
		
		designCredits=(TextView)findViewById(R.id.design_credits);
		developerCredits=(TextView)findViewById(R.id.develop_credits);
		nfnLabs=(TextView)findViewById(R.id.nfn);
		
		designCredits.setMovementMethod(LinkMovementMethod.getInstance());
		developerCredits.setMovementMethod(LinkMovementMethod.getInstance());
		nfnLabs.setMovementMethod(LinkMovementMethod.getInstance());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0)
		{
			if(modeChanged)
			{
				GamePlay.activity.finish();
				startActivity(new Intent(SettingsActivity.this,GamePlay.class));
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
