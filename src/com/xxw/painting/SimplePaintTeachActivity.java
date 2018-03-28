package com.xxw.painting;

import android.app.Activity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.xxw.painting.utils.DataDao;
import com.xxw.painting.views.ChangeView;
import com.xxw.painting.views.ColorPickerDialog;
import com.xxw.painting.views.ColorPickerView.OnColorChangedListener;
import com.xxw.painting.widgets.RoundRectDrawable;
import com.xxw.painting.views.MyPaintView;
import com.xxw.painting.views.ToolView;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class SimplePaintTeachActivity extends Activity  implements OnSeekBarChangeListener{
    public static final String BACKGROUND_COLOR_KEY = "backgroud_color";
    static final int BYHAND = 6;
    static final int CIRCLE = 1;
    static final int LINE = 3;
    static final int NONE = 0;
    static final int OVAL = 5;
    static final int POINT = 4;
    static final int RECT = 2;
    private static final int SAVE_MENU_ID = 5;
    private static final int SEND_MENU_ID = 6;
    public static final String STROKE_COLOR_KEY = "stroke_color";
    public static final String STROKE_WIDTH_KEY = "stroke_width";
    private static final int black = 6;
    private static final int blue = 4;
    private static final int gray = 5;
    private static final int green = 3;
    private static final int orange = 1;
    private static final int red = 0;
    private static final int yellow = 2;
    private ChangeView Example;
    private ChangeView IMV;
    public int exampleID;
    public int imgEasyID;
    private Paint mPaint;
    private int mPaintColor = -15724528;
    private int mSavedColor;
    private int mSeekBarValue = 0;
    private int mStrokeWidth = 3;
    private String mTargetDirectory = null;
    private String mTargetFilePath = null;
    private TextView mTextView = null;
    private MyPaintView mView = null;
    private ToolView tv;
    private ToolView way;

    class ClearClick implements OnClickListener {
        ClearClick() {
        }

        public void onClick(View v) {
        	SimplePaintTeachActivity.this.mView.reset();
        }
    }

    class ColorClick implements OnClickListener {
        private int iNum;

        ColorClick(int Num) {
            this.iNum = Num;
        }

        public void onClick(View v) {
            switch (this.iNum) {
                case 0:
                	SimplePaintTeachActivity.this.mPaintColor = -65536;
                	SimplePaintTeachActivity.this.setStrokeColor(SimplePaintTeachActivity.this.mPaintColor);
                    return;
                case 1:
                	SimplePaintTeachActivity.this.setStrokeColor(-755424);
                	SimplePaintTeachActivity.this.lastColor(-755424);
                    return;
                case 2:
                	SimplePaintTeachActivity.this.setStrokeColor(-256);
                	SimplePaintTeachActivity.this.lastColor(-256);
                    return;
                case 3:
                	SimplePaintTeachActivity.this.setStrokeColor(-8407026);
                	SimplePaintTeachActivity.this.lastColor(-8407026);
                    return;
                case 4:
                	SimplePaintTeachActivity.this.setStrokeColor(-16737578);
                	SimplePaintTeachActivity.this.lastColor(-16737578);
                    return;
                case 5:
                	SimplePaintTeachActivity.this.setStrokeColor(-8039775);
                	SimplePaintTeachActivity.this.lastColor(-8039775);
                    return;
                case 6:
                	SimplePaintTeachActivity.this.setStrokeColor(-16777216);
                	SimplePaintTeachActivity.this.lastColor(-16777216);
                    return;
                default:
                    return;
            }
        }
    }

    private class DialogCancelClick implements DialogInterface.OnClickListener {
        private DialogCancelClick() {
        }

        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            paramDialogInterface.cancel();
        }
    }

    private class DialogConfirmClick implements DialogInterface.OnClickListener {
        private DialogConfirmClick() {
        }

        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
        	SimplePaintTeachActivity.this.mStrokeWidth = SimplePaintTeachActivity.this.mSeekBarValue;
        	SimplePaintTeachActivity.this.mPaint.setStrokeWidth((float) SimplePaintTeachActivity.this.mStrokeWidth);
            paramDialogInterface.cancel();
        }
    }

    private class DoClick implements OnClickListener {
        private DoClick() {
        }

        public void onClick(View v) {
        	SimplePaintTeachActivity.this.mView.doFunction();
        }
    }

    class EraserClick implements OnClickListener {
        EraserClick() {
        }

        public void onClick(View v) {
        	SimplePaintTeachActivity.this.lastColor(SimplePaintTeachActivity.this.mPaint.getColor());
        	SimplePaintTeachActivity.this.mView.setToolMode(6);
        	SimplePaintTeachActivity.this.mPaint.setMaskFilter(null);
            if (SimplePaintTeachActivity.this.mView.getBackgroundColor() != 0) {
            	SimplePaintTeachActivity.this.mPaint.setColor(SimplePaintTeachActivity.this.mView.getBackgroundColor());
            } else {
            	SimplePaintTeachActivity.this.mPaint.setColor(-1);
            }
        }
    }

    private class ExPositiveClick implements DialogInterface.OnClickListener {
        private ExPositiveClick() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (!SimplePaintTeachActivity.this.mView.isSaved() && SimplePaintTeachActivity.this.saveFile()) {
                Toast.makeText(SimplePaintTeachActivity.this, SimplePaintTeachActivity.this.getString(R.string.message_saved_success, new Object[]{SimplePaintTeachActivity.this.mTargetFilePath}), 1).show();
            }
            SimplePaintTeachActivity.this.finish();
        }
    }

    class HandClick implements OnClickListener {
        HandClick() {
        }

        public void onClick(View v) {
        	SimplePaintTeachActivity.this.setLastColor(SimplePaintTeachActivity.this.mSavedColor);
        	SimplePaintTeachActivity.this.mView.setToolMode(6);
        	SimplePaintTeachActivity.this.way.setVisibility(4);
        }
    }

    private class NegativeClick implements DialogInterface.OnClickListener {
        private NegativeClick() {
        }

        public void onClick(DialogInterface paramDialogInterface, int which) {
            paramDialogInterface.cancel();
        }
    }

    private class NeutralClick implements DialogInterface.OnClickListener {
        private NeutralClick() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            SimplePaintTeachActivity.this.finish();
        }
    }

    private class PositiveClick implements DialogInterface.OnClickListener {
        private PositiveClick() {
        }

        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
        	SimplePaintTeachActivity.this.saveFile();
            if (new File(SimplePaintTeachActivity.this.mTargetFilePath).exists()) {
                Intent localIntent1 = new Intent("android.intent.action.SEND");
                localIntent1.setType("image/jpeg");
                localIntent1.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(SimplePaintTeachActivity.this.mTargetFilePath)));
                SimplePaintTeachActivity.this.startActivity(Intent.createChooser(localIntent1, SimplePaintTeachActivity.this.getText(R.string.message_choose_send)));
            }
        }
    }

    private class StrokeColorChangedListener implements OnColorChangedListener {
        private StrokeColorChangedListener() {
        }

        public void colorChanged(int paramInt) {
        	SimplePaintTeachActivity.this.mPaintColor = paramInt;
        	SimplePaintTeachActivity.this.mPaint.setColor(SimplePaintTeachActivity.this.mPaintColor);
        	SimplePaintTeachActivity.this.lastColor(SimplePaintTeachActivity.this.mPaintColor);
        }
    }

    class StrokeWidthClick implements OnClickListener {
        StrokeWidthClick() {
        }

        public void onClick(View v) {
        	SimplePaintTeachActivity.this.showDialog(R.layout.seekbar);
        }
    }

    private class UndoClick implements OnClickListener {
        private UndoClick() {
        }

        public void onClick(View v) {
        	SimplePaintTeachActivity.this.mView.undoFuction();
        }
    }

    class WayClick implements OnClickListener {
        WayClick() {
        }

        public void onClick(View v) {
        	SimplePaintTeachActivity.this.setLastColor(SimplePaintTeachActivity.this.mSavedColor);
        	SimplePaintTeachActivity.this.way.setVisibility(0);
        }
    }

    private boolean saveFile() {
        StringBuilder sB = new StringBuilder(String.valueOf(this.mTargetDirectory));
        sB.append(File.separator);
        sB.append(new Date().getTime());
        this.mTargetFilePath = sB.toString() + ".jpg";
        File localFile = new File(this.mTargetFilePath);
        if (!localFile.exists()) {
            try {
                localFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean isSuccessful = this.mView.save(localFile);
        if (!isSuccessful) {
            Toast.makeText(this, getText(R.string.message_saved_failed), 1).show();
        }
        return isSuccessful;
    }

    private void setBackgroundColor(int paramInt) {
        this.mView.setBackgroundColor(paramInt);
    }

    private void setStrokeColor(int paramInt) {
        this.mPaintColor = paramInt;
        this.mPaint.setColor(this.mPaintColor);
    }

    private void setStrokeWidth(int paramInt) {
        this.mStrokeWidth = paramInt;
        this.mSeekBarValue = paramInt;
        this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
    }

    private int lastColor(int color) {
        this.mSavedColor = color;
        return this.mSavedColor;
    }

    private void setLastColor(int lastcolor) {
        if (lastcolor == 0) {
            lastcolor = -15724528;
        }
        this.mPaint.setColor(lastcolor);
    }

    public void applyPreference() {
        int i = this.mPaintColor;
        int j = this.mStrokeWidth;
        int k = this.mView.getBackgroundColor();
        if (i != this.mPaintColor) {
            setStrokeColor(i);
        }
        if (j != this.mStrokeWidth) {
            setStrokeWidth(j);
        }
        if (k != this.mView.getBackgroundColor()) {
            setBackgroundColor(k);
        }
        Log.d(getClass().getSimpleName(), "(color:" + i + ", width:" + j + ", backgroundColor:" + k + ")");
    }

    protected void promptdialog() {
        Builder builder = new Builder(this).setMessage("开始绘画前,请选择左下角的徒手作画或者绘图工具~\n谢谢您的支持和使用~").setIcon(R.drawable.width).setTitle("友情提示");
        builder.setPositiveButton("确认", new NegativeClick());
        builder.create().show();
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
	        Intent intent = getIntent();
	        int id = intent.getIntExtra("ID",0);
	        this.imgEasyID = DataDao.stepicno[id];
	        this.exampleID = DataDao.icno[id];
	        requestWindowFeature(Window.FEATURE_NO_TITLE);   
			getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
	        setContentView(R.layout.activity_simplepaintingteach);
	        this.mView = (MyPaintView) findViewById(R.id.paint_view);
	        this.mPaint = new Paint();
	        this.mPaint.setAntiAlias(true);
	        this.mPaint.setDither(true);
	        this.mPaint.setColor(this.mPaintColor);
	        this.mPaint.setStyle(Style.STROKE);
	        this.mPaint.setStrokeJoin(Join.ROUND);
	        this.mPaint.setStrokeCap(Cap.ROUND);
	        this.mPaint.setStrokeWidth((float) this.mStrokeWidth);
	        this.mView.setPaint(this.mPaint);
	        this.mView.setToolMode(6);
	        this.IMV = (ChangeView) findViewById(R.id.changeview);
	        this.IMV.setImgEasyID(this.imgEasyID);
	        this.IMV.setVisibility(View.VISIBLE);
	        this.IMV.touchEasy(this.imgEasyID);
	        this.IMV.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	SimplePaintTeachActivity.this.IMV.setVisibility(View.INVISIBLE);
	            	SimplePaintTeachActivity.this.Example.setVisibility(View.VISIBLE);
	            }
	        });
	        this.Example = (ChangeView) findViewById(R.id.changedepict);
	        this.Example.setImgDepictID(this.exampleID);
	        this.Example.touchExample(this.exampleID);
	        this.Example.setVisibility(View.INVISIBLE);
	        this.Example.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	SimplePaintTeachActivity.this.IMV.setVisibility(0);
	            	SimplePaintTeachActivity.this.Example.setVisibility(4);
	            }
	        });
	        this.way = (ToolView) findViewById(R.id.wayview);
	        this.tv = (ToolView) findViewById(R.id.tool_view);
	        this.way.addItem("圆形", R.drawable.circle, new OnClickListener() {
	            public void onClick(View v) {
	            	SimplePaintTeachActivity.this.setLastColor(SimplePaintTeachActivity.this.mSavedColor);
	            	SimplePaintTeachActivity.this.mView.setToolMode(1);
	            }
	        }, new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.way.addItem("矩形", R.drawable.rect, new OnClickListener() {
	            public void onClick(View v) {
	            	SimplePaintTeachActivity.this.setLastColor(SimplePaintTeachActivity.this.mSavedColor);
	            	SimplePaintTeachActivity.this.mView.setToolMode(2);
	            }
	        }, new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.way.addItem("直线", R.drawable.lines, new OnClickListener() {
	            public void onClick(View v) {
	            	SimplePaintTeachActivity.this.setLastColor(SimplePaintTeachActivity.this.mSavedColor);
	            	SimplePaintTeachActivity.this.mView.setToolMode(3);
	            }
	        }, new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.way.addItem("椭圆", R.drawable.oval, new OnClickListener() {
	            public void onClick(View v) {
	            	SimplePaintTeachActivity.this.setLastColor(SimplePaintTeachActivity.this.mSavedColor);
	            	SimplePaintTeachActivity.this.mView.setToolMode(5);
	            }
	        }, new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.way.addItem("点", R.drawable.point, new OnClickListener() {
	            public void onClick(View v) {
	            	SimplePaintTeachActivity.this.setLastColor(SimplePaintTeachActivity.this.mSavedColor);
	            	SimplePaintTeachActivity.this.mView.setToolMode(4);
	            }
	        }, new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.way.setVisibility(View.GONE);
	        this.tv.addItem("笔刷", R.drawable.brush, new HandClick(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.tv.addItem("工具", R.drawable.tools, new WayClick(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.tv.addItem("宽度", R.drawable.width, new StrokeWidthClick(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.tv.addItem("颜色", R.drawable.color, new OnClickListener() {
	            public void onClick(View v) {
	                new ColorPickerDialog(SimplePaintTeachActivity.this, new StrokeColorChangedListener(), SimplePaintTeachActivity.this.mPaint.getColor()).show();
	                SimplePaintTeachActivity.this.lastColor(SimplePaintTeachActivity.this.mPaint.getColor());
	            }
	        }, new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.tv.addItem("撤消", R.drawable.undo, new UndoClick(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.tv.addItem("恢复", R.drawable.redo, new DoClick(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.tv.addItem("清屏", R.drawable.clear, new ClearClick(), new RoundRectDrawable(Color.argb(255, 43, 137, 219), Color.argb(255, 29, 107, 192), Color.argb(255, 30, 107, 192), Color.argb(255, 57, 157, 225), 0.5f), null);
	        this.tv.setVisibility(View.VISIBLE);
	        EmbossMaskFilter embossMaskFilter = new EmbossMaskFilter(new float[]{1.0f, 1.0f, 1.0f}, 0.1f, 8.0f, 5.0f);
	        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(8.0f, Blur.NORMAL);
	        blurMaskFilter = new BlurMaskFilter(8.0f, Blur.OUTER);
	        File localFile2 = new File(Environment.getExternalStorageDirectory(), "course");
	        localFile2.mkdirs();
	        this.mTargetDirectory = localFile2.getAbsolutePath();
	        applyPreference();

	}
	protected Dialog onCreateDialog(int paramInt) {
        if (paramInt != R.layout.seekbar) {
            return null;
        }
        View localView = ((LayoutInflater) getSystemService("layout_inflater")).inflate(paramInt, null);
        SeekBar sWidth = (SeekBar) localView.findViewById(R.id.seekbar);
        this.mTextView = (TextView) localView.findViewById(R.id.stroke_width);
        sWidth.setProgress(this.mStrokeWidth);
        this.mSeekBarValue = this.mStrokeWidth;
        this.mTextView.setText(String.valueOf(this.mSeekBarValue));
        sWidth.setOnSeekBarChangeListener(this);
        Builder dialogBuilder = new Builder(this);
        dialogBuilder.setView(localView);
        dialogBuilder.setTitle(getText(R.string.dialog_title_stroke_width));
        dialogBuilder.setPositiveButton(getText(R.string.button_yes), new DialogConfirmClick());
        dialogBuilder.setNegativeButton(getText(R.string.button_no), new DialogCancelClick());
        return dialogBuilder.create();
    }

    protected void dialog() {
        Builder builder = new Builder(this).setMessage("确认退出吗？").setIcon(R.drawable.ic_launcher).setTitle("提示");
        ExPositiveClick exitpositiveClick = new ExPositiveClick();
        NeutralClick neutralClick = new NeutralClick();
        NegativeClick negativeClick = new NegativeClick();
        builder.setPositiveButton("保存后退出", exitpositiveClick);
        builder.setNeutralButton("直接退出", neutralClick);
        builder.setNegativeButton("取消", negativeClick);
        builder.create().show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getRepeatCount() == 0) {
            dialog();
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu paramMenu) {
        super.onCreateOptionsMenu(paramMenu);
        MenuItem localMenuItem11 = paramMenu.add(0, 5, 0, R.string.menu_save);
        MenuItem localMenuItem12 = paramMenu.add(0, 6, 0, R.string.menu_send);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        this.mPaint.setXfermode(null);
        this.mPaint.setAlpha(255);
        switch (item.getItemId()) {
            case 5:
                if (!this.mView.isSaved() && saveFile()) {
                    Toast.makeText(this, getString(R.string.message_saved_success, new Object[]{this.mTargetFilePath}), 1).show();
                    break;
                }
            case 6:
                if (this.mView.isSaved() && this.mTargetFilePath != null) {
                    if (new File(this.mTargetFilePath).exists()) {
                        Intent sendIntent = new Intent("android.intent.action.SEND");
                        sendIntent.setType("image/jpeg");
                        sendIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(this.mTargetFilePath)));
                        startActivity(Intent.createChooser(sendIntent, getText(R.string.message_choose_send)));
                        break;
                    }
                }
                Builder dialogBuilder = new Builder(this).setTitle(R.string.message_save_file_title).setMessage(R.string.message_save_file_prompt);
                dialogBuilder.setPositiveButton(R.string.button_positive, new PositiveClick());
                dialogBuilder.setNegativeButton(R.string.button_negative, new NegativeClick()).show();
                break;
                
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
        Log.d(getClass().getSimpleName(), "onPause");
    }

    public boolean onPrepareOptionsMenu(Menu paramMenu) {
        super.onPrepareOptionsMenu(paramMenu);
        return true;
    }

    public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean) {
        this.mSeekBarValue = paramInt;
        this.mTextView.setText(String.valueOf(this.mSeekBarValue));
    }

    protected void onResume() {
        super.onResume();
    }

    public void onStartTrackingTouch(SeekBar paramSeekBar) {
    }

    public void onStopTrackingTouch(SeekBar paramSeekBar) {
    }


}
