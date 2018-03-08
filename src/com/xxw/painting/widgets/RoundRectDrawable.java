package com.xxw.painting.widgets;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class RoundRectDrawable extends Drawable {
	private int downendcolor;
    private int downstartcolor;
    private float position;
    private int upendcolor;
    private int upstartcolor;
    public RoundRectDrawable(int upstartcolor, int upendcolor, int downstartcolor, int downendcolor, float position) {
        this.upstartcolor = upstartcolor;
        this.upendcolor = upendcolor;
        this.position = position;
        this.downstartcolor = downstartcolor;
        this.downendcolor = downendcolor;
    }

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		Rect bounds = getBounds();
        Paint paint = new Paint();
        int colors[] = new int[4];
        float[] positions = new float[]{this.upstartcolor, 0.0f, this.upendcolor, this.position};
        colors[2] = this.downstartcolor;
        positions[2] = this.position;
        colors[3] = this.downendcolor;
        positions[3] = 1.0f;
        paint.setShader(new LinearGradient((float) bounds.left, (float) bounds.top, (float) bounds.left, (float) bounds.bottom, colors, positions, TileMode.CLAMP));
        Path path = new Path();
        path.moveTo((float) bounds.left, (float) bounds.top);
        path.addRoundRect(new RectF((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom), 20.0f, 20.0f, Direction.CW);
        canvas.drawPath(path, paint);

	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		// TODO Auto-generated method stub

	}

}
