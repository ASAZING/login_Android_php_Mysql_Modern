package com.asazing.loginui.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asazing.loginui.R;

import java.util.ArrayList;

class MyAdapter extends BaseAdapter {

    ArrayList<Schedule> list;
    String[] listItem = {"1 texto 1","7 texto 1","2 texto 1","8 texto 1","3 texto 1","9 texto 1","4 texto 1","10 texto 1","5 texto 1","11 texto 1","6 texto 1","12 texto" };
    private Context context;

    MyAdapter(Context context) {
        this.context = context;
        list = new ArrayList();
        //Resources resources = context.getResources();
        for (int count = 0; count < 12; count++) {
            Schedule tempSchedule = new Schedule( listItem[count]);
            list.add(tempSchedule);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Schedule tempSchedule = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_text, parent, false);
        }
        TextView item = (TextView) convertView.findViewById(R.id.txt_col1);
        item.setText(tempSchedule.Item);

        return convertView;
    }
    class Schedule {
        String Item;

        Schedule(String item ) {
            this.Item = item;

        }
    }
}
