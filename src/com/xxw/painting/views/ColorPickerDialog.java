package com.xxw.painting.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.xxw.painting.R;
import com.xxw.painting.views.ColorPickerView.OnColorChangedListener;

public class ColorPickerDialog extends Dialog {
    private int mInitialColor;
    private OnColorChangedListener mListener;

    private class ColorPickerDialogOnColorChangedListener implements OnColorChangedListener {
        private ColorPickerDialogOnColorChangedListener() {
        }

        public void colorChanged(int color) {
            ColorPickerDialog.this.mListener.colorChanged(color);
            ColorPickerDialog.this.dismiss();
        }
    }

    public ColorPickerDialog(Context paramContext, OnColorChangedListener paramOnColorChangedListener, int paramInt) {
        super(paramContext);
        this.mListener = paramOnColorChangedListener;
        this.mInitialColor = paramInt;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ColorPickerView(getContext(), new ColorPickerDialogOnColorChangedListener(), this.mInitialColor));
        setTitle(getContext().getText(R.string.dialog_title_color_picker));
    }
}
