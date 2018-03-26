package com.xxw.painting.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ChangeView extends LinearLayout {
    private int imgDepictID;
    private int imgEasyID;
    private Context mContext;

    public ChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOrientation(1);
    }

    public ChangeView(Context context) {
        super(context);
        setOrientation(1);
        this.mContext = context;
    }

    public void setImgEasyID(int imgEasyID) {
        this.imgEasyID = imgEasyID;
    }

    public void setImgDepictID(int imgDepictID) {
        this.imgDepictID = imgDepictID;
    }

    public void touchExample(int imgEasyID) {
        ImageView iv = new ImageView(this.mContext);
        Bitmap bm = BitmapFactory.decodeStream(getResources().openRawResource(imgEasyID));
        int width = bm.getWidth();
        int height = bm.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f, 1.0f);
        iv.setImageBitmap(Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true));
        addView(iv, new LayoutParams(-2, -2, 1.0f));
    }

    public void touchEasy(int imgEasyID) {
        ImageView iv = new ImageView(this.mContext);
        Bitmap bm = BitmapFactory.decodeStream(getResources().openRawResource(imgEasyID));
        int width = bm.getWidth();
        int height = bm.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(0.75f, 0.75f);
        iv.setImageBitmap(Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true));
        addView(iv, new LayoutParams(-2, -2, 1.0f));
    }

    public void depictMode(int imgDepictID) {
        ImageView depict = new ImageView(this.mContext);
        depict.setImageResource(imgDepictID);
        addView(depict, new LayoutParams(-2, -2, 1.0f));
    }
}
