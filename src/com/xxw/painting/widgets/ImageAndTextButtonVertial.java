package com.xxw.painting.widgets;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


public class ImageAndTextButtonVertial extends LinearLayout {
	   private Drawable bg;
	    private Drawable hbg;
	    private int htc;
	    private int tc;
	    private TextView tv;

	    private class TouchListener implements OnTouchListener {
	        private TouchListener() {
	        }

	        public boolean onTouch(View v, MotionEvent event) {
	            if (event.getAction() == 0) {
	            	ImageAndTextButtonVertial.this.setBackgroundDrawable(ImageAndTextButtonVertial.this.hbg);
	            }
	            if (event.getAction() == 1) {
	            	ImageAndTextButtonVertial.this.setBackgroundDrawable(ImageAndTextButtonVertial.this.bg);
	            }
	            return false;
	        }
	    }

	    public ImageAndTextButtonVertial(Context context, int imgres, String text) {
	        super(context);
	        setOrientation(1);
	        setGravity(1);
	        ImageView iv = new ImageView(context);
	        iv.setImageResource(imgres);
	        iv.setScaleType(ScaleType.FIT_CENTER);
	        addView(iv, new LayoutParams(-2, -2));
	        this.tv = new TextView(context);
	        addView(this.tv, new LayoutParams(-2, -2));
	        this.tv.setGravity(17);
	        this.tv.setText(text);
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
