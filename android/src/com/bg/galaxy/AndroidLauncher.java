package com.bg.galaxy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler{
	private AdView adView;
	private InterstitialAd interstitial;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		MobileAds.initialize(this, "ca-app-pub-9648937932604603~5376105576");

		RelativeLayout layout = new RelativeLayout(this);

		requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		View gameView  = initializeForView(new Galaxy(this),config);
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-9648937932604603/1195227578");
		AdRequest adRequest = new AdRequest.Builder().build();
		//adRequest.isTestDevice(this);
		adView.loadAd(adRequest);

		layout.addView(gameView);

		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(adView, adParams);

		setContentView(layout);
		//initialize(new Tetris(), config);
	}

	public void LoadAD(){
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId("ca-app-pub-9648937932604603/5135585979");

		// Создание запроса объявления.
		AdRequest adRequest1 = new AdRequest.Builder().build();

		// Запуск загрузки межстраничного объявления.
		interstitial.loadAd(adRequest1);
	}

	public void showInterstitial(int mode){
		handler1.sendEmptyMessage(mode);
	}

	public Handler handler1 = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what==1){
				interstitial.show();
			}else{
				LoadAD();
			}
		}
	};

	@Override
	public void showAdMob(boolean show) {
		handler.sendEmptyMessage(show ? 1:0);
	}

	public Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(msg.what == 0) {adView.setVisibility(View.GONE);}
			if(msg.what == 1){
				adView.setVisibility(View.VISIBLE);
				AdRequest adRequest = new AdRequest.Builder().build();
				adView.loadAd(adRequest);
			}
		}
	};
}
