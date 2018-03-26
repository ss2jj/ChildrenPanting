package com.xxw.painting.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.Toast;
import com.xxw.painting.utils.DrawingStep;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyPaintView extends PaintView {
    static final int BYHAND = 6;
    static final int CIRCLE = 1;
    static final int ERASERWAY = 7;
    static final int LIMITSTEP = 9;
    static final int LINE = 3;
    static final int NONE = 0;
    static final int OVAL = 5;
    static final int POINT = 4;
    static final int RECT = 2;
    private static final float TOUCH_TOLERANCE = 2.0f;
    private int availableStep = -1;
    public float cr;
    public float cx;
    public float cy;
    public float ex;
    public float ey;
    private int iMode;
    private int mBackgroundColor = NONE;
    private Bitmap mBitmap;
    private Paint mBitmapPaint;
    private Canvas mCanvas;
    private int mHeight;
    private boolean mNeedSave = false;
    private Paint mPaint = null;
    public Path mPath;
    private int mWidth;
    private float mX;
    private float mY;
    public int stepPointer = -1;
    private DrawingStep[] steps;
    public float sx;
    public float sy;

    public MyPaintView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public MyPaintView(Context paramContext) {
        super(paramContext);
    }

    private int computeSampleSize(Options options, int width, int height) {
        int i = options.outWidth;
        int j = options.outHeight;
        int n = Math.max(i / width, j / height);
        if (n > CIRCLE && i > width && i / n < width) {
            n--;
        } else if (n > CIRCLE && j > height && j / n < height) {
            n--;
        }
        return n < CIRCLE ? CIRCLE : n;
    }

    private Bitmap decodeBitmap(String imgPath, int width, int height) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);
        options.inSampleSize = computeSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inScaled = true;
        options.inPreferredConfig = Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
        if (bitmap.getWidth() <= width && bitmap.getHeight() <= height) {
            return bitmap;
        }
        int bmWidth = bitmap.getWidth();
        int bmHeight = bitmap.getHeight();
        int resizedWidth = width;
        int resizedHeight = height;
        if (((float) (bmWidth / width)) <= ((float) (bmHeight / height))) {
            resizedWidth = (bmWidth * height) / bmHeight;
        } else {
            resizedHeight = (bmHeight * width) / bmWidth;
        }
        Bitmap rt = Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, true);
        if (rt == null) {
            return bitmap;
        }
        bitmap.recycle();
        return rt;
    }

    private int measureHeight(int paramInt) {
        return MeasureSpec.getSize(paramInt);
    }

    private int measureWidth(int paramInt) {
        return MeasureSpec.getSize(paramInt);
    }

    private void touch_move(float paramFloat1, float paramFloat2) {
        float f2 = Math.abs(paramFloat1 - this.mX);
        float f4 = Math.abs(paramFloat2 - this.mY);
        if (f2 >= TOUCH_TOLERANCE || f4 >= TOUCH_TOLERANCE) {
            this.mPath.quadTo(this.mX, this.mY, (this.mX + paramFloat1) / TOUCH_TOLERANCE, (this.mY + paramFloat2) / TOUCH_TOLERANCE);
            this.mX = paramFloat1;
            this.mY = paramFloat2;
        }
    }

    private void touch_start(float paramFloat1, float paramFloat2) {
        this.mPath.reset();
        this.mPath.moveTo(paramFloat1, paramFloat2);
        this.mX = paramFloat1;
        this.mY = paramFloat2;
    }

    public int setToolMode(int choose) {
        this.iMode = choose;
        return this.iMode;
    }

    private void touch_up() {
        this.mPath.lineTo(this.mX, this.mY);
        this.mCanvas.drawPath(this.mPath, this.mPaint);
        this.mPath.reset();
    }

    public void drawBitmap(String paramString) {
        if (paramString != null) {
            this.mBitmap.eraseColor(this.mBackgroundColor);
            this.mPath.reset();
            Bitmap decodeBitmap = decodeBitmap(paramString, this.mWidth, this.mHeight);
            Rect rect = new Rect(NONE, NONE, decodeBitmap.getWidth(), decodeBitmap.getHeight());
            int i1 = NONE;
            int i2 = NONE;
            int i3 = this.mWidth;
            int i4 = this.mHeight;
            if (this.mWidth > decodeBitmap.getWidth()) {
                i2 = (this.mWidth - decodeBitmap.getWidth()) / RECT;
                i3 = i2 + decodeBitmap.getWidth();
            }
            if (this.mHeight > decodeBitmap.getHeight()) {
                i1 = (this.mHeight - decodeBitmap.getHeight()) / RECT;
                i4 = i1 + decodeBitmap.getHeight();
            }
            rect = new Rect(i2, i1, i3, i4);
            this.mCanvas.drawBitmap(decodeBitmap, rect, rect, this.mBitmapPaint);
        }
    }

    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public boolean isSaved() {
        if (this.mNeedSave) {
            return false;
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.mBitmapPaint);
        drawStepToScreen(canvas);
    }

    private void drawStep(Canvas localCanvas, DrawingStep step) {
        switch (step.iMode) {
            case CIRCLE /*1*/:
                localCanvas.drawCircle(step.cx, step.cy, step.cr, step.paint);
                return;
            case RECT /*2*/:
                localCanvas.drawRect(step.sx, step.sy, step.ex, step.ey, step.paint);
                return;
            case LINE /*3*/:
                localCanvas.drawLine(step.sx, step.sy, step.ex, step.ey, step.paint);
                return;
            case POINT /*4*/:
                localCanvas.drawPoint(step.sx, step.sy, step.paint);
                return;
            case OVAL /*5*/:
                localCanvas.drawOval(new RectF(step.sx, step.sy, step.ex, step.ey), step.paint);
                return;
            case BYHAND /*6*/:
                localCanvas.drawPath(step.path, step.paint);
                return;
            default:
                return;
        }
    }

    private void drawStepToBitmap(DrawingStep step) {
        drawStep(this.mCanvas, step);
    }

    private void drawStepToScreen(Canvas canvas) {
        for (int i = NONE; i <= this.stepPointer; i += CIRCLE) {
            drawStep(canvas, this.steps[i]);
        }
    }

    public void undoFuction() {
        if (this.stepPointer >= 0) {
            this.stepPointer -= CIRCLE;
            invalidate();
            return;
        }
        Toast.makeText(getContext(), "没有可以撤销的步骤了~", NONE).show();
    }

    public void doFunction() {
        if (this.stepPointer < this.availableStep) {
            this.stepPointer += CIRCLE;
            invalidate();
            return;
        }
        Toast.makeText(getContext(), "没有可以恢复的步骤了~", NONE).show();
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        this.mWidth = measureWidth(paramInt1);
        this.mHeight = measureHeight(paramInt2);
        this.mBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Config.ARGB_8888);
        this.mBitmap.eraseColor(this.mBackgroundColor);
        this.mCanvas = new Canvas(this.mBitmap);
        this.mPath = new Path();
        this.mBitmapPaint = new Paint(POINT);
        this.steps = new DrawingStep[LIMITSTEP];
        setMeasuredDimension(this.mWidth, this.mHeight);
    }

    protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }

    public void reset() {
        if (this.mBitmap != null) {
            this.mBitmap.eraseColor(this.mBackgroundColor);
            this.mPath.reset();
            this.sy = 0.0f;
            this.sx = 0.0f;
            this.ey = 0.0f;
            this.ex = 0.0f;
            this.cy = 0.0f;
            this.cx = 0.0f;
            this.stepPointer = -1;
            invalidate();
        }
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
        int i = NONE;
        while (i <= this.stepPointer) {
            drawStepToBitmap(this.steps[i]);
            i += CIRCLE;
        }
        while (i <= this.availableStep) {
            this.steps[i - this.stepPointer] = this.steps[i];
            i += CIRCLE;
        }
        this.availableStep = (this.availableStep - this.stepPointer) - CIRCLE;
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

    public void setBackgroundColor(int paramInt) {
        this.mBackgroundColor = paramInt;
        reset();
    }

    public void setPaint(Paint paramPaint) {
        this.mPaint = paramPaint;
    }

    public void setBitmapPaint(Paint paramPaint) {
        this.mBitmapPaint = paramPaint;
    }

    public boolean onTouchEvent(MotionEvent event) {
        DrawingStep localStep;
        switch (event.getAction() & 255) {
            case NONE /*0*/:
                this.sx = event.getX();
                this.sy = event.getY();
                this.mPath.reset();
                this.mPath.moveTo(this.sx, this.sy);
                this.mX = this.sx;
                this.mY = this.sy;
                if (this.stepPointer != 8) {
                    this.stepPointer += CIRCLE;
                    break;
                }
                drawStepToBitmap(this.steps[NONE]);
                for (int i = NONE; i < 8; i += CIRCLE) {
                    this.steps[i] = this.steps[i + CIRCLE];
                }
                break;
            case CIRCLE /*1*/:
                this.ex = event.getX();
                this.ey = event.getY();
                this.mPath.lineTo(this.mX, this.mY);
                localStep = new DrawingStep();
                localStep.cr = this.cr;
                localStep.cx = this.cx;
                localStep.cy = this.cy;
                localStep.sx = this.sx;
                localStep.sy = this.sy;
                localStep.ex = this.ex;
                localStep.ey = this.ey;
                localStep.iMode = this.iMode;
                localStep.paint = new Paint();
                localStep.paint.set(this.mPaint);
                localStep.path = new Path();
                localStep.path.set(this.mPath);
                this.steps[this.stepPointer] = localStep;
                this.availableStep = this.stepPointer;
                invalidate();
                this.mIsRealDrawDone = true;
                break;
            case RECT /*2*/:
                this.ex = event.getX();
                this.ey = event.getY();
                touch_move(this.ex, this.ey);
                this.cx = (this.sx + this.ex) / TOUCH_TOLERANCE;
                this.cy = (this.sy + this.ey) / TOUCH_TOLERANCE;
                this.cr = (float) Math.sqrt((double) (((this.sx - this.cx) * (this.sx - this.cx)) + ((this.sy - this.cy) * (this.sy - this.cy))));
                localStep = new DrawingStep();
                localStep.cr = this.cr;
                localStep.cx = this.cx;
                localStep.cy = this.cy;
                localStep.sx = this.sx;
                localStep.sy = this.sy;
                localStep.ex = this.ex;
                localStep.ey = this.ey;
                localStep.iMode = this.iMode;
                localStep.paint = new Paint();
                localStep.paint.set(this.mPaint);
                localStep.path = new Path();
                localStep.path.set(this.mPath);
                this.steps[this.stepPointer] = localStep;
                invalidate();
                break;
        }
        this.mNeedSave = true;
        return true;
    }
}
