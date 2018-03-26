package com.xxw.painting.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

public class ColorPickerView extends View {
    private static final int CENTER_RADIUS = 32;
    private static final int CENTER_X = 100;
    private static final int CENTER_Y = 100;
    private static final float PI = 3.141593f;
    private Paint mCenterPaint;
    private final int[] mColors = new int[]{-65536, -65281, -16776961, -16711681, -16711936, -256, -65536, -16777216, -1, -16777215};
    private boolean mHighlightCenter;
    private OnColorChangedListener mListener;
    private Paint mPaint;
    private boolean mTrackingCenter;

    public interface OnColorChangedListener {
        void colorChanged(int i);
    }

    ColorPickerView(Context c, OnColorChangedListener l, int color) {
        super(c);
        this.mListener = l;
        Shader s = new SweepGradient(0.0f, 0.0f, this.mColors, null);
        this.mPaint = new Paint(1);
        this.mPaint.setShader(s);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(32.0f);
        this.mCenterPaint = new Paint(1);
        this.mCenterPaint.setColor(color);
        this.mCenterPaint.setStrokeWidth(5.0f);
    }

    public int getColor() {
        return this.mCenterPaint.getColor();
    }

    protected void onDraw(Canvas canvas) {
        float r = 100.0f - (this.mPaint.getStrokeWidth() * 0.5f);
        canvas.translate(100.0f, 100.0f);
        canvas.drawOval(new RectF(-r, -r, r, r), this.mPaint);
        canvas.drawCircle(0.0f, 0.0f, 32.0f, this.mCenterPaint);
        if (this.mTrackingCenter) {
            int c = this.mCenterPaint.getColor();
            this.mCenterPaint.setStyle(Style.STROKE);
            if (this.mHighlightCenter) {
                this.mCenterPaint.setAlpha(255);
            } else {
                this.mCenterPaint.setAlpha(128);
            }
            canvas.drawCircle(0.0f, 0.0f, this.mCenterPaint.getStrokeWidth() + 32.0f, this.mCenterPaint);
            this.mCenterPaint.setStyle(Style.FILL);
            this.mCenterPaint.setColor(c);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(200, 200);
    }

    private int floatToByte(float x) {
        return Math.round(x);
    }

    private int pinToByte(int n) {
        if (n < 0) {
            return 0;
        }
        if (n > 255) {
            return 255;
        }
        return n;
    }

    private int ave(int s, int d, float p) {
        return Math.round(((float) (d - s)) * p) + s;
    }

    private int interpColor(int[] colors, float unit) {
        if (unit <= 0.0f) {
            return colors[0];
        }
        if (unit >= 1.0f) {
            return colors[colors.length - 1];
        }
        float p = unit * ((float) (colors.length - 1));
        int i = (int) p;
        p -= (float) i;
        int c0 = colors[i];
        int c1 = colors[i + 1];
        return Color.argb(ave(Color.alpha(c0), Color.alpha(c1), p), ave(Color.red(c0), Color.red(c1), p), ave(Color.green(c0), Color.green(c1), p), ave(Color.blue(c0), Color.blue(c1), p));
    }

    private int rotateColor(int color, float rad) {
        float deg = (180.0f * rad) / 3.1415927f;
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        ColorMatrix cm = new ColorMatrix();
        ColorMatrix tmp = new ColorMatrix();
        cm.setRGB2YUV();
        tmp.setRotate(0, deg);
        cm.postConcat(tmp);
        tmp.setYUV2RGB();
        cm.postConcat(tmp);
        float[] a = cm.getArray();
        return Color.argb(Color.alpha(color), pinToByte(floatToByte(((a[0] * ((float) r)) + (a[1] * ((float) g))) + (a[2] * ((float) b)))), pinToByte(floatToByte(((a[5] * ((float) r)) + (a[6] * ((float) g))) + (a[7] * ((float) b)))), pinToByte(floatToByte(((a[10] * ((float) r)) + (a[11] * ((float) g))) + (a[12] * ((float) b)))));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r12) {
        /*
        r11 = this;
        r10 = 0;
        r6 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r9 = 1;
        r5 = r12.getX();
        r3 = r5 - r6;
        r5 = r12.getY();
        r4 = r5 - r6;
        r5 = r3 * r3;
        r6 = r4 * r4;
        r5 = r5 + r6;
        r5 = (double) r5;
        r5 = java.lang.Math.sqrt(r5);
        r7 = 4629700416936869888; // 0x4040000000000000 float:0.0 double:32.0;
        r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1));
        if (r5 > 0) goto L_0x0029;
    L_0x0020:
        r1 = r9;
    L_0x0021:
        r5 = r12.getAction();
        switch(r5) {
            case 0: goto L_0x002b;
            case 1: goto L_0x0066;
            case 2: goto L_0x0035;
            default: goto L_0x0028;
        };
    L_0x0028:
        return r9;
    L_0x0029:
        r1 = r10;
        goto L_0x0021;
    L_0x002b:
        r11.mTrackingCenter = r1;
        if (r1 == 0) goto L_0x0035;
    L_0x002f:
        r11.mHighlightCenter = r9;
        r11.invalidate();
        goto L_0x0028;
    L_0x0035:
        r5 = r11.mTrackingCenter;
        if (r5 == 0) goto L_0x0043;
    L_0x0039:
        r5 = r11.mHighlightCenter;
        if (r5 == r1) goto L_0x0028;
    L_0x003d:
        r11.mHighlightCenter = r1;
        r11.invalidate();
        goto L_0x0028;
    L_0x0043:
        r5 = (double) r4;
        r7 = (double) r3;
        r5 = java.lang.Math.atan2(r5, r7);
        r0 = (float) r5;
        r5 = 1086918620; // 0x40c90fdc float:6.283186 double:5.3700915E-315;
        r2 = r0 / r5;
        r5 = 0;
        r5 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1));
        if (r5 >= 0) goto L_0x0057;
    L_0x0054:
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r2 = r2 + r5;
    L_0x0057:
        r5 = r11.mCenterPaint;
        r6 = r11.mColors;
        r6 = r11.interpColor(r6, r2);
        r5.setColor(r6);
        r11.invalidate();
        goto L_0x0028;
    L_0x0066:
        r5 = r11.mTrackingCenter;
        if (r5 == 0) goto L_0x0028;
    L_0x006a:
        if (r1 == 0) goto L_0x0077;
    L_0x006c:
        r5 = r11.mListener;
        r6 = r11.mCenterPaint;
        r6 = r6.getColor();
        r5.colorChanged(r6);
    L_0x0077:
        r11.mTrackingCenter = r10;
        r11.invalidate();
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.funnybo.view.ColorPickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }
}
