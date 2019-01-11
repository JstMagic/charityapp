package com.kodemakers.charity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.kodemakers.charity.R;

import java.util.List;

public class RoleSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context context;
    private List<String> asr;

    public RoleSpinnerAdapter(Context context, List<String> asr) {
        this.asr = asr;
        this.context = context;
    }


    public int getCount() {
        return asr.size();
    }

    public Object getItem(int i) {
        return asr.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(context);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(14);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(asr.get(position));
        txt.setTextColor(Color.parseColor("#000000"));
        txt.setBackgroundColor(Color.parseColor("#ffffff"));
        return txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(context);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(14);
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);
        txt.setText(asr.get(i));
        txt.setTextColor(Color.parseColor("#000000"));
        txt.setBackgroundColor(Color.parseColor("#ffffff"));
        return txt;
    }
}
