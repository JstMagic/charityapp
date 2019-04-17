package com.kodemakers.charity.fonts;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kodemakers.charity.app.MyApplication;

public class RobotoNormalTextView extends AppCompatTextView {

    public RobotoNormalTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public RobotoNormalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public RobotoNormalTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getRobotoRegularFont());
        }
    }
}