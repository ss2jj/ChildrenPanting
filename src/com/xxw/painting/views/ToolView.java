package com.xxw.painting.views;

import com.xxw.painting.widgets.ImageAndTextButton;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ToolView extends LinearLayout {
    private Context mContext;

    public ToolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(0);
        this.mContext = context;
    }

    public ToolView(Context context) {
        super(context);
        setOrientation(0);
        this.mContext = context;
    }

    public void addItem(String item, int imgres, OnClickListener clicklistener, Drawable hbg, Drawable bg) {
        ImageAndTextButton btn = new ImageAndTextButton(this.mContext, imgres, item);
        addView(btn, new LayoutParams(-2, -2, 1.0f));
        btn.setOnClickListener(clicklistener);
        btn.setBackground(hbg, bg);
    }
}
