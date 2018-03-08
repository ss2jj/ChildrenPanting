package com.xxw.painting.widgets;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageAndTextButton extends LinearLayout {
	 private Drawable bg;
	 private Drawable hbg;
	 private int htc;
	 private int tc;
	 private TextView tv;
	 private class TouchListener implements OnTouchListener {
	        private TouchListener() {
	        }
	        @Override	
	        public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == 0) {
	            	ImageAndTextButton.this.setBackgroundDrawable(ImageAndTextButton.this.hbg);
	            }
	            if (event.getAction() == 1) {
	            	ImageAndTextButton.this.setBackgroundDrawable(ImageAndTextButton.this.bg);
	            }
	            return false;
	        }

	    }
	 
	public ImageAndTextButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	 public ImageAndTextButton(Context context, int imgres, String text) {
		  super(context);
		  setOrientation(HORIZONTAL);
	        setGravity(Gravity.CENTER);
	        addView(new TextView(context), new LayoutParams(10, -2, 1.0f));
	        ImageView iv = new ImageView(context);
	        iv.setImageResource(imgres);
	        iv.setScaleType(ScaleType.FIT_CENTER);
	        addView(iv, new LayoutParams(-2, -2, 1.0f));
	        addView(new TextView(context), new LayoutParams(10, -2, 1.0f));
	        this.tv = new TextView(context);
	        addView(this.tv, new LayoutParams(-2, -2));
	        this.tv.setGravity(16);
	        this.tv.setTextColor(-16777216);
	        this.tv.setTextSize(22.0f);
	        this.tv.setSingleLine(true);
	        this.tv.setText(text);
	        addView(new TextView(context), new LayoutParams(10, -2, 1.0f));
	        setOnTouchListener(new TouchListener());

	 }
	 
	
	 public void setTextColors(int htc, int tc) {
	        this.htc = htc;
	        this.tc = tc;
	        this.tv.setTextColor(tc);
	    }

	    public void setBackground(Drawable hbg, Drawable bg) {
	        this.hbg = hbg;
	        this.bg = bg;
	        setBackgroundDrawable(bg);
	    }


}
