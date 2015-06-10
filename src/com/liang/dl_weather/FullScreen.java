package com.liang.dl_weather;

import com.qq.e.splash.SplashAd;
import com.qq.e.splash.SplashAdListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

public class FullScreen extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen);
		
		FrameLayout container = (FrameLayout) this.findViewById(R.id.FullScreen);
		
		new SplashAd( this,container, Constants.appID,Constants.splashID, new SplashAdListener() {
			
			@Override
			public void onAdPresent() {
				// TODO Auto-generated method stub
				Log.i("fullScreen", "onAdPresent");
				//Toast.makeText( FullScreen.this , "Ready Go!", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onAdFailed(int arg0) {
				// TODO Auto-generated method stub
				Log.i("fullScreen", "onAdFailed" + arg0);
				//Toast.makeText(FullScreen.this,"Fail" + arg0, Toast.LENGTH_LONG).show();
				FullScreen.this.finish();
				
				Intent intent = new Intent(FullScreen.this,MainActivity.class);
				
				startActivity(intent);
			}
			
			@Override
			public void onAdDismissed() {
				// TODO Auto-generated method stub
				Log.i("fullScreen", "onAdDismissed");
				Toast.makeText(FullScreen.this,"Ready Go", Toast.LENGTH_SHORT).show();
				FullScreen.this.finish();
				
				Intent intent = new Intent(FullScreen.this,MainActivity.class);
				
				startActivity(intent);
			}
		} );
		
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
