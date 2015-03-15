package com.achyuthnfn.cnbfinal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.widget.Toast;


public class CowsAndBulls
{
	public int cows,bulls;
	public String requiredWord;
	static List<Character> charsChecked;
	MediaPlayer mp=new MediaPlayer();
	Context context;
	SettingsEditor settingsEditor;
	Boolean soundOn;
	
	public CowsAndBulls(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		settingsEditor=new SettingsEditor(context);
	}
	
	public int[] computeCowsAndBulls(String randomWord,String userInput)
	{
			cows=0;
            bulls=0;
            requiredWord=randomWord;
            int[] result=new int[2];
            charsChecked=new ArrayList<Character>();
            
            soundOn=settingsEditor.getSoundPref();
            
            for(int i=0;i<userInput.length();i++)
            {
            	if(userInput.charAt(i)==randomWord.charAt(i))
                {
                    bulls+=1;
                    if(!(charsChecked.contains(userInput.charAt(i))))
                    {
                        charsChecked.add(userInput.charAt(i));
                    }
                }
            }
            for(int i=0;i<userInput.length();i++)
            {
                for(int j=0;j<randomWord.length();j++)
                {
                    if(userInput.charAt(i)==randomWord.charAt(j))
                    {
                        if(i!=j)
                        {
                            if(!(charsChecked.contains(userInput.charAt(i))))
                            {
                                cows+=1;
                                charsChecked.add(userInput.charAt(i));
                            }
                        }
                        else
                        {continue;}
                    }
                }
            }
            result[0]=bulls;
            result[1]=cows;
            if(soundOn)
            {
	            if(result[0]!=0 && result[1]==0)
				{
					if(result[0]==userInput.length())
					{
						playMedia("raw/win.mp3");
					}
					else
					{playMedia("raw/green.mp3");}
				}
				else if((result[0]!=0 && result[1]!=0)||(result[0]==0 && result[1]!=0))
				{
					playMedia("raw/yellow.mp3");
				}
				else
				{
					playMedia("raw/red.mp3");
				}
            }
            charsChecked.clear();
            return result;
	}
	
	public void playMedia(String path) 
	{
		try
		{
			if(mp.isPlaying())
			{
				mp.stop();
				mp.release();
				mp=new MediaPlayer();
				Toast.makeText(context, ""+path, Toast.LENGTH_SHORT).show();
			}
			
			AssetFileDescriptor descriptor= context.getAssets().openFd(path);
			mp.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
			mp.prepare();
			mp.start();
			
			mp.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mp.release();
				}
			});
		}catch(Exception e){e.printStackTrace();}
	}
}
