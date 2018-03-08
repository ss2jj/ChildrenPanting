package com.xxw.painting.views;

import com.xxw.painting.widgets.ImageAndTextButton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewChoose extends LinearLayout {
	private Context mContext;

	public ViewChoose(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.setOrientation(LinearLayout.VERTICAL);
	    this.setGravity(Gravity.CENTER);
	}
	public ViewChoose(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.setOrientation(LinearLayout.VERTICAL);
	    this.setGravity(Gravity.CENTER);
	}
	
	 public void addItem(String item, int imgres, OnClickListener clicklistener, Drawable hbg, Drawable bg) {
	        addView(new TextView(this.mContext), new LayoutParams(10, 5, 1.0f));
	        ImageAndTextButton btn = new ImageAndTextButton(this.mContext, imgres, item);
	        addView(btn, new LayoutParams(250, -2, 5.0f));
	        addView(new TextView(this.mContext), new LayoutParams(10, 2, 1.0f));
	        btn.setOnClickListener(clicklistener);
	        btn.setBackground(hbg, bg);
	    }


}
