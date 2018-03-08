package com.xxw.painting;

import com.xxw.painting.views.ViewChoose;
import com.xxw.painting.widgets.RoundRectDrawable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity {
	 private ViewChoose choosetv;
	  private class ColorClickListener implements OnClickListener {
	        private ColorClickListener() {
	        }

	        public void onClick(View v) {
	          /*  Intent intent = new Intent();
	            intent.setClass(LittleAnim.this, ColorDraw.class);
	            LittleAnim.this.startActivity(intent);
	            LittleAnim.this.overridePendingTransition(R.anim.fade, R.anim.fade);*/
	        }
	    }

	    private class DoodleClickListener implements OnClickListener {
	        private DoodleClickListener() {
	        }

	        public void onClick(View v) {
	           /* Intent intent = new Intent();
	            intent.setClass(LittleAnim.this, FunnyCourse.class);
	            LittleAnim.this.startActivity(intent);
	            LittleAnim.this.overridePendingTransition(R.anim.fade, R.anim.fade);*/
	        }
	    }

	    private class SimplePaintingClickListener implements OnClickListener {
	        private SimplePaintingClickListener() {
	        }

	        public void onClick(View v) {
	           Intent intent = new Intent();
	            intent.setClass(MainActivity.this, SimplePaintActivity.class);
	            MainActivity.this.startActivity(intent);
	           
	        }
	    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_main);
		 this.choosetv = (ViewChoose) findViewById(R.id.toolview);
	        this.choosetv.addItem("简笔画", 17301566, new SimplePaintingClickListener(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), new RoundRectDrawable(Color.argb(205, 234, 246, 253), Color.argb(205, 217, 240, 252), Color.argb(205, 188, 229, 252), Color.argb(205, 167, 217, 248), 0.5f));
	        this.choosetv.addItem("涂色卡", 17301567, new ColorClickListener(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), new RoundRectDrawable(Color.argb(205, 253, 240, 219), Color.argb(205, 250, 223, 211), Color.argb(205, 250, 203, 197), Color.argb(205, 253, 206, 200), 0.5f));
	        this.choosetv.addItem("随心涂", 17301562, new DoodleClickListener(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), new RoundRectDrawable(Color.argb(205, 243, 237, 119), Color.argb(205, 219, 226, 97), Color.argb(205, 192, 217, 82), Color.argb(205, 157, 204, 59), 0.5f));

	}


	
}
