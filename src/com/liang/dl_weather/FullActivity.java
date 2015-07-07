package com.liang.dl_weather;

import net.youmi.android.AdManager;
import net.youmi.android.spot.SplashView;
import net.youmi.android.spot.SpotDialogListener;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class FullActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		AdManager.getInstance(this).init(Constants.appID, Constants.appSecret, false);
		
		SpotManager.getInstance(this).loadSplashSpotAds();
		SplashView splashView = new SplashView(this,MainActivity.class);
		
		setContentView(splashView.getSplashView());
		
		SpotManager.getInstance(this).showSplashSpotAds(this, splashView, new SpotDialogListener() {
			
			@Override
			public void onSpotClosed() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onShowSuccess() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onShowFailed() {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
