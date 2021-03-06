package com.example.wheeler.jamessummer2017finalproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wheeler.jamessummer2017finalproject.R;
import com.example.wheeler.jamessummer2017finalproject.Task;

import java.util.ArrayList;

/**
 * Created by wheeler on 7/17/17.
 */

public class TaskListAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final ArrayList<Task> list;

    public TaskListAdapter(Context context, ArrayList<Task> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.drawer_list_item, parent, false);
        TextView tv = (TextView) convertView.findViewById(R.id.drawer_list_tv);
        tv.setText(list.get(position).getName());
        return tv;
    }
}
