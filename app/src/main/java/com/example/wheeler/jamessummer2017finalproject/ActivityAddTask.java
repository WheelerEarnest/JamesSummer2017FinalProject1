package com.example.wheeler.jamessummer2017finalproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.wheeler.jamessummer2017finalproject.adapters.BaseViewPagerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityAddTask extends AppCompatActivity {
    private  ViewPager viewPager;
    private Task editTask;
    ArrayList<Fragment> timePager = new ArrayList<>();

    @BindView(R.id.new_task_name)
    EditText name;

    @BindView(R.id.new_task_description)
    EditText description;

//    @BindView(R.id.new_task_time_picker)
//    TimePicker time;
//
//    @BindView(R.id.new_task_date_picker)
//    DatePicker date;

    @OnClick(R.id.new_task_accept)
    public void accept(View v){
        Task task = new Task(name.getText().toString(), description.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        DatePicker date = (DatePicker)findViewById(R.id.new_task_date_picker);
//        TimePicker time = (TimePicker) findViewById(R.id.new_task_time_picker);
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDayOfMonth();
        String time = year + "/" + month + "/" + day;
        Date due = null;
        try {
            due = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task.setDue(due);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Task", task);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }
    @OnClick(R.id.new_task_cancel)
    public void cancel(View v){
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);
        timePager.add(new DateFrag());
        timePager.add(new TimeFrag());
        viewPager = (ViewPager) findViewById(R.id.new_task_time_pager);
        viewPager.setAdapter(new BaseViewPagerAdapter(getSupportFragmentManager(), timePager));
        viewPager.setCurrentItem(0);

    }

}
