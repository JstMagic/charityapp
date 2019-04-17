package com.kodemakers.charity.fonts;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kodemakers.charity.app.MyApplication;

public class RobotoMediumTextView extends AppCompatTextView {

    public RobotoMediumTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        if (context != null && !isInEditMode()) {
            setTypeface(MyApplication.getRobotoMediumFont());
        }
    }
}