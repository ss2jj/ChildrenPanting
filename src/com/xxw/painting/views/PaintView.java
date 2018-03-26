package com.xxw.painting.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.xxw.painting.utils.BitmapUtil;
import com.xxw.painting.utils.PaintingStep;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PaintView extends View {
    static final int LIMITSTEP = 9;
    private int availableStep;
    protected Bitmap mBitmap;
    protected BitmapUtil mBitmapUtil;
    private int mFillingColor;
    protected int mHeight;
    protected boolean mIsRealDrawDone;
    private boolean mNeedSave;
    private Paint mPaint;
    protected int[] mPixels;
    private int mResId;
    private float mTouchedX;
    private float mTouchedY;
    protected UserActivityListener mUserActivityListener;
    protected int mWidth;
    private int stepPointer;
    private PaintingStep[] steps;

    public interface UserActivityListener {
        void userActivityHappen();
    }

    static {
        System.loadLibrary("bitmaputil");
    }

    public PaintView(Context context) {
        super(context);
        this.mNeedSave = false;
        this.mPaint = null;
        this.stepPointer = -1;
        this.availableStep = -1;
        this.mTouchedX = -1.0f;
        this.mTouchedY = -1.0f;
        this.mIsRealDrawDone = false;
        this.steps = new PaintingStep[LIMITSTEP];
    }

    public PaintView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        this.mNeedSave = false;
        this.mPaint = null;
        this.stepPointer = -1;
        this.availableStep = -1;
        this.mTouchedX = -1.0f;
        this.mTouchedY = -1.0f;
        this.mIsRealDrawDone = false;
        this.steps = new PaintingStep[LIMITSTEP];
    }

    public PaintView(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        this.mNeedSave = false;
        this.mPaint = null;
        this.stepPointer = -1;
        this.availableStep = -1;
        this.mTouchedX = -1.0f;
        this.mTouchedY = -1.0f;
        this.mIsRealDrawDone = false;
        this.steps = new PaintingStep[LIMITSTEP];
    }

    private void createScaledBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), this.mResId);
        int j = bitmap.getWidth();
        int k = bitmap.getHeight();
        float f6 = Math.min(((float) this.mHeight) / ((float) k), ((float) this.mWidth) / ((float) j));
        Matrix matrix = new Matrix();
        boolean flag = matrix.postScale(f6, f6);
        Bitmap decodeBitmap = Bitmap.createBitmap(bitmap, 0, 0, j, k, matrix, true);
        this.mBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Config.ARGB_8888);
        this.mBitmap.eraseColor(-1);
        Canvas canvas = new Canvas(this.mBitmap);
        Rect rect = new Rect(0, 0, decodeBitmap.getWidth(), decodeBitmap.getHeight());
        int i1 = 0;
        int i2 = 0;
        int i3 = this.mWidth;
        int i4 = this.mHeight;
        if (this.mWidth > decodeBitmap.getWidth()) {
            i2 = (this.mWidth - decodeBitmap.getWidth()) / 2;
            i3 = i2 + decodeBitmap.getWidth();
        }
        if (this.mHeight > decodeBitmap.getHeight()) {
            i1 = (this.mHeight - decodeBitmap.getHeight()) / 2;
            i4 = i1 + decodeBitmap.getHeight();
        }
        canvas.drawBitmap(decodeBitmap, rect, new Rect(i2, i1, i3, i4), null);
        bitmap.recycle();
    }

    public boolean isSaved() {
        if (this.mNeedSave) {
            return false;
        }
        return true;
    }

    public boolean save(File paramFile) {
        boolean rt;
        try {
            paramFile.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(paramFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.mBitmap.compress(CompressFormat.JPEG, 100, fo);
        try {
            fo.flush();
            rt = true;
            this.mNeedSave = false;
        } catch (IOException e2) {
            e2.printStackTrace();
            rt = false;
        }
        try {
            fo.close();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return rt;
    }

    public void setPaint(Paint paramPaint) {
        this.mPaint = paramPaint;
    }

    private void initAtFirstDraw() {
        getWidthAndHeight();
        createScaledBitmap();
        initBackgroundDrawable();
    }

    public void reset() {
        if (this.mBitmap != null) {
            initAtFirstDraw();
            invalidate();
        }
    }

    public void archiveCurrentCache() {
    }

    protected void bitmapToPixels() {
        this.mBitmap.getPixels(this.mPixels, 0, this.mWidth, 0, 0, this.mWidth, this.mHeight);
    }

    protected void pixelsToBitmap() {
        this.mBitmap.setPixels(this.mPixels, 0, this.mWidth, 0, 0, this.mWidth, this.mHeight);
    }

    public int getPixelColor(int paramInt1, int paramInt2) {
        return this.mPixels[(this.mWidth * paramInt2) + paramInt1];
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    protected void getWidthAndHeight() {
        this.mWidth = getWidth();
        this.mHeight = getHeight();
    }

    protected void initBackgroundDrawable() {
        this.mPixels = new int[(this.mWidth * this.mHeight)];
        bitmapToPixels();
        this.mBitmapUtil = new BitmapUtil(this.mPixels, this.mWidth, this.mHeight);
        this.mBitmapUtil.drawBoundary(-16777216);
        pixelsToBitmap();
    }

    protected boolean isInitialized() {
        if (this.mBitmap != null) {
            return true;
        }
        return false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInitialized()) {
            initAtFirstDraw();
        }
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, null);
    }

    protected void onSizeChanged(int i, int j, int k, int l) {
        super.onSizeChanged(i, j, k, l);
    }

    public boolean onTouchEvent(MotionEvent motionevent) {
        if (motionevent.getAction() == 0) {
            this.mTouchedX = motionevent.getX();
            this.mTouchedY = motionevent.getY();
            int i = (int) this.mTouchedX;
            int j = (int) this.mTouchedY;
            BitmapUtil bitmaputil = this.mBitmapUtil;
            int lastColor = getPixelColor(i, j);
            int nextColor = this.mFillingColor;
            PaintingStep localStep = new PaintingStep();
            localStep.x = i;
            localStep.y = j;
            localStep.lastColor = lastColor;
            localStep.nextColor = nextColor;
            if (this.stepPointer == 8) {
                for (int k = 0; k < 8; k++) {
                    this.steps[k] = this.steps[k + 1];
                }
            } else {
                this.stepPointer++;
            }
            this.steps[this.stepPointer] = localStep;
            this.availableStep = this.stepPointer;
            this.mIsRealDrawDone = bitmaputil.fill(i, j, this.mPixels, this.mWidth, this.mHeight, this.mFillingColor, -16777216);
            pixelsToBitmap();
            invalidate();
        }
        return super.onTouchEvent(motionevent);
    }

    public void undoFuction() {
        if (this.stepPointer >= 0) {
            PaintingStep localStep = this.steps[this.stepPointer];
            this.mBitmapUtil.fill(localStep.x, localStep.y, this.mPixels, this.mWidth, this.mHeight, localStep.lastColor, -16777216);
            this.stepPointer--;
            pixelsToBitmap();
            invalidate();
            return;
        }
        Toast.makeText(getContext(), "没有可以撤销的步骤了~", 0).show();
    }

    public void doFunction() {
        if (this.stepPointer < this.availableStep) {
            this.stepPointer++;
            PaintingStep localStep = this.steps[this.stepPointer];
            this.mBitmapUtil.fill(localStep.x, localStep.y, this.mPixels, this.mWidth, this.mHeight, localStep.nextColor, -16777216);
            pixelsToBitmap();
            invalidate();
            return;
        }
        Toast.makeText(getContext(), "没有可以恢复的步骤了~", 0).show();
    }

    public void setBackground(int i) {
        this.mResId = i;
    }

    public void setFillingColor(int i) {
        this.mFillingColor = i;
    }

    public void setUserActivityListener(UserActivityListener useractivitylistener) {
        this.mUserActivityListener = useractivitylistener;
    }
}