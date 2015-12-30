package com.liang.dl_weather;
import com.qq.e.ads.splash.*;

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
		new SplashAD(this, container, Constants.appID, Constants.splashID, new SplashADListener() {
			
			@Override
			public void onNoAD(int arg0) {
				// TODO Auto-generated method stub
				Log.i("fullScreen", "onAdFailed" + arg0);
				//Toast.makeText(FullScreen.this,"Fail" + arg0, Toast.LENGTH_LONG).show();
				FullScreen.this.finish();
				
				Intent intent = new Intent(FullScreen.this,MainActivity.class);
				
				startActivity(intent);
			}
			
			@Override
			public void onADPresent() {
				// TODO Auto-generated method stub
				Log.i("fullScreen", "onAdPresent");
			}
			
			@Override
			public void onADDismissed() {
				// TODO Auto-generated method stub
				Log.i("fullScreen", "onAdDismissed");
				Toast.makeText(FullScreen.this,"Ready Go", Toast.LENGTH_SHORT).show();
				FullScreen.this.finish();
				
				Intent intent = new Intent(FullScreen.this,MainActivity.class);
				
				startActivity(intent);
			}
		});
		
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
