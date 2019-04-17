package com.kodemakers.charity.fonts;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.kodemakers.charity.app.MyApplication;

public class RobotoNormalEditText extends AppCompatEditText {

    public RobotoNormalEditText(Context context) {
        super(context);
        setTypeface(context);
    }

    public RobotoNormalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public RobotoNormalEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getRobotoRegularFont());
        }
    }
}