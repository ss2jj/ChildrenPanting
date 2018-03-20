package com.xxw.painting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.xxw.painting.views.ViewChoose;
import com.xxw.painting.widgets.RoundRectDrawable;

public class SplashActivity extends Activity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable(){    
		    public void run() {    
		    //execute the task 
		    	SplashActivity.this.startActivity(new Intent(SplashActivity.this,MainActivity.class));	
		    }    
		 }, 2000);
	}


	
}
