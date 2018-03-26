package com.xxw.painting.utils;

import android.graphics.Point;
import java.util.LinkedList;

public class BitmapUtil {
    int c;
    private int mBoundaryColor;
    private int mFillColor;
    int mHeight;
    int[] mPixels;
    LinkedList mQueue = new LinkedList();
    int mWidth;

    public native boolean fill(int i, int i2, int[] iArr, int i3, int i4, int i5, int i6);

    public BitmapUtil(int[] paramArrayOfInt, int paramInt1, int paramInt2) {
        this.mPixels = paramArrayOfInt;
        this.mWidth = paramInt1;
        this.mHeight = paramInt2;
        preProcessBMP();
    }

    private boolean isCanNotFilt(int line, int columns) {
        this.c = this.mPixels[(this.mWidth * columns) + line];
        if (this.c == this.mBoundaryColor || this.c == this.mFillColor) {
            return true;
        }
        return false;
    }

    private boolean isToBeFilt(int line, int columns) {
        this.c = this.mPixels[(this.mWidth * columns) + line];
        if (this.c == this.mBoundaryColor || this.c == this.mFillColor) {
            return false;
        }
        return true;
    }

    private void preProcessBMP() {
        for (int i = 0; i < this.mWidth * this.mHeight; i++) {
            if (this.mPixels[i] < -5592406) {
                this.mPixels[i] = -16777216;
            }
        }
    }

   public  void drawBoundary(int boundaryColor) {
        int i = this.mHeight - 1;
        int j = this.mWidth - 1;
        for (int k = 0; k < this.mHeight; k++) {
            setPixelColor(0, k, boundaryColor);
            setPixelColor(j, k, boundaryColor);
        }
        for (int m = 0; m < this.mWidth; m++) {
            setPixelColor(m, 0, boundaryColor);
            setPixelColor(m, i, boundaryColor);
        }
    }

    public int getPixelColor(int paramInt1, int paramInt2) {
        return this.mPixels[(this.mWidth * paramInt2) + paramInt1];
    }

    public int getPixelColor(Point paramPoint) {
        return this.mPixels[(paramPoint.y * this.mWidth) + paramPoint.x];
    }

    public void setPixelColor(int paramInt1, int paramInt2, int paramInt3) {
        this.mPixels[(this.mWidth * paramInt2) + paramInt1] = paramInt3;
    }
}
