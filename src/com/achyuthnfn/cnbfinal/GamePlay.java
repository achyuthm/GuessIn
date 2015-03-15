package com.achyuthnfn.cnbfinal;


// activity displayed when game in progress..
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class GamePlay extends Activity
{
	ListView listView;
	RulesListAdapter rulesListAdapter;
	AttemptsListAdapter attemptsListAdapter;
	EditText userInput;
	boolean firstLoad;
	List<UserValues> attemptedWords;
	FetchWord fetchWord;
	int maxLength,attempts;
	String randomWord;
	CowsAndBulls cnbcomputer;
	UserValues values;
	TextView attemptsCount,statusText,newGameIconText,menuIconText;
	RelativeLayout newGameIcon,menuIcon;
	Boolean soundOn;
	int gameMode;
	SettingsEditor settingsEditor;
	final Context context=this;
	public static Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		activity=this;
		
		firstLoad=true;
		attempts=0;
		fetchWord=new FetchWord(this);
		
		settingsEditor=new SettingsEditor(this);
		gameMode=settingsEditor.getGameMode();
		soundOn=settingsEditor.getSoundPref();
		
		settingsEditor.setGamesCount();
		
		statusText=(TextView)findViewById(R.id.status_text);
		
		Typeface fontProximaRegular=Typeface.createFromAsset(getAssets(), "ProximaNova-Regular.otf");
		statusText.setTypeface(fontProximaRegular);
		
		Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
		
		menuIcon=(RelativeLayout)findViewById(R.id.menuicon);
		
		menuIconText=(TextView)findViewById(R.id.menuicontext);
		menuIconText.setTypeface(font);
		
		newGameIcon=(RelativeLayout)findViewById(R.id.newgame_icon);
		newGameIcon.setVisibility(View.GONE);
		newGameIconText=(TextView)findViewById(R.id.newgameicontext);
		newGameIconText.setTypeface(font);
		
		newGameIcon.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				// display dialog when user presses reload option..
				AlertDialog.Builder builder=new AlertDialog.Builder(GamePlay.this);
				builder.setTitle("New Game");
				builder.setMessage("Sure you want to start a new game?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast toast=Toast.makeText(getBaseContext(), "The word was: "+randomWord+" ", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
						finish();
						startActivity(getIntent());
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				
				AlertDialog alertDialog=builder.create();
				alertDialog.show();
			}
		});
		
		DatabaseAdapter db_adap = new DatabaseAdapter(getApplicationContext());
		String[] rulesList=new String[]{"Right letters in the right place","Right letters in the wrong place","All letters entered are wrong"};
		listView=(ListView)findViewById(R.id.my_list_view);
		listView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// to hide keyboard when user touches the list
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
				return false;
			}
		});
		userInput=(EditText)findViewById(R.id.user_input);
		userInput.setTypeface(fontProximaRegular);
		InputFilter[] fArray = new InputFilter[1]; // to set edit text limit
		rulesListAdapter=new RulesListAdapter(this, rulesList,"ProximaNova-Regular.otf");
		listView.setAdapter(rulesListAdapter);
		attemptedWords=new ArrayList<UserValues>();
		db_adap.createDatabase();
		fetchWord.openDatabase();
		
		// get a random word of given string length based on game mode, fetched from settings..
		switch(gameMode)
		{
			case 0:
				// Easy mode
				randomWord=fetchWord.getRandomWord("____");
				randomWord=randomWord.toLowerCase();
				statusText.setText(getResources().getString(R.string.fourletterword));
				fArray[0] = new InputFilter.LengthFilter(4);
				userInput.setFilters(fArray); // sets the limit of characters on edit text view
				maxLength=4;
				break;
			case 1:
				// Moderate mode
				randomWord=fetchWord.getRandomWord("______");
				randomWord=randomWord.toLowerCase();
				statusText.setText(getResources().getString(R.string.sixletterword));
				fArray[0] = new InputFilter.LengthFilter(6);
				userInput.setFilters(fArray);
				maxLength=6;
				break;
			case 2:
				// Difficult mode
				randomWord=fetchWord.getRandomWord("________");
				randomWord=randomWord.toLowerCase();
				statusText.setText(getResources().getString(R.string.eightletterword));
				fArray[0] = new InputFilter.LengthFilter(8);
				userInput.setFilters(fArray);
				maxLength=8;
				break;
		}
		
		
//		Toast.makeText(getBaseContext(), randomWord, Toast.LENGTH_SHORT).show();
		
		
		fetchWord.closeDataBase();
		userInput.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				if(firstLoad==true)
				{
					// to display rules at the start of each game
					firstLoad=false;
					listView.setAdapter(attemptsListAdapter);
				}
			}
		});
		userInput.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
			{
				// TODO Auto-generated method stub
				String inputWord=userInput.getText().toString();
				if(actionId==EditorInfo.IME_ACTION_DONE)
				{
					// checks to be performed when user enters a word
					if(userInput.getText().toString()==null || userInput.getText().length()<maxLength)
					{
						Toast.makeText(getBaseContext(), "Please enter a "+maxLength+" letter word", Toast.LENGTH_SHORT).show();
					}
					else if(allCharsSame(inputWord))
					{
						Toast.makeText(getBaseContext(), "Enter a valid word.", Toast.LENGTH_SHORT).show();
					}
					else
					{
						if(newGameIcon.getVisibility()==View.GONE)
						{
							// make new game icon visible when attempts>1
							newGameIcon.setVisibility(View.VISIBLE);
						}
						
						attempts++;
						int[] result= null;
						values = new UserValues();
						cnbcomputer = new CowsAndBulls(getApplicationContext());
						result=cnbcomputer.computeCowsAndBulls(randomWord, inputWord);
						values.setword(inputWord);
						values.setbull(result[0]);
						values.setcow(result[1]);

						attemptedWords.add(0,values);
						attemptsListAdapter=new AttemptsListAdapter(GamePlay.this, attemptedWords, "ProximaNova-Regular.otf");
						listView.setAdapter(attemptsListAdapter);
						userInput.setText("");
						if(attempts>=2)
						{
							statusText.setText(Integer.toString(attempts)+" attempts");
						}
						
						switch(gameMode)
						{
						case 0:
							settingsEditor.setLettersCount(4);
							break;
						case 1:
							settingsEditor.setLettersCount(6);
							break;
						case 2:
							settingsEditor.setLettersCount(8);
							break;
						}
						
						settingsEditor.setWordCount();
						
						if(result[0]==inputWord.length())
						{
							// if word has been guessed correctly
							fetchWord.openDatabase();
							fetchWord.insertScore(inputWord, attempts);
							fetchWord.closeDataBase();
							Intent i=new Intent(GamePlay.this,Glory.class);
							i.putExtra("WORD", randomWord);
							i.putExtra("ATTEMPTS", Integer.toString(attempts));
							startActivity(i);
							finish();
						}
						return true;
					}
				}
				return false;
			}
		});
		
		menuIcon.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				Intent intent=new Intent(GamePlay.this,SettingsActivity.class);
				intent.putExtra("GAMESTARTED", true);
				startActivity(intent);
			}
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_play, menu);
		return true;
	}
	
	public boolean allCharsSame(String word)
	{
		int count=0;
		for(int i=0;i<word.length();i++)
		{
			if(word.charAt(i)==word.charAt(0))
			{
				count+=1;
			}
		}
		if(count==word.length())
		{return true;}
		else
		{return false;}
	}
	

}
