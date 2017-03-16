package com.example.androidcodes.photoeditingapp.Frames.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidcodes.photoeditingapp.R;


public class ListAdapter extends BaseAdapter {

    Context context;

    String[] font_arr;

    LayoutInflater inflater;

    public ListAdapter(Context context, String[] font_arr) {

        this.font_arr = font_arr;

        this.context = context;

        inflater = ((LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    public int getCount() {
        return this.font_arr.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {

        ViewHolder viewholder;

        View view1 = view;

        if (view == null) {

            view1 = this.inflater.inflate(R.layout.select_fonts_to_add_text, null);

            viewholder = new ViewHolder();

            viewholder.text = (TextView) view1.findViewById(R.id.txtFont);

            view1.setTag(viewholder);

        } else {

            viewholder = (ViewHolder) view1.getTag();

        }

        viewholder.text.setTypeface(Typeface.createFromAsset(context.getAssets(), font_arr[i]));
        viewholder.text.setText("add_text");

        return view1;
    }

    public static class ViewHolder {

        public TextView text;

    }
}
