package com.example.wheeler.jamessummer2017finalproject.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.wheeler.jamessummer2017finalproject.Task;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wheeler on 7/3/17.
 */

public class TestService extends Service {


    private MediaPlayer mediaPlayer = new MediaPlayer();

    private Timer timer = new Timer();
    private int update = 0;
    public static final String UPDATE = "update";
    public static final String ACTION = "TestAction";
    public static final String AUDIO = "AUDIO";
    private String url;
    private Task nearest;
    private Date due;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        nearest = bundle.getParcelable("Task");
        due = nearest.getDue();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class UpdateTask extends TimerTask{
        public void run(){
            Intent intent = new Intent();
            intent.setAction(ACTION);
            intent.putExtra(UPDATE, ++update);
            sendBroadcast(intent);
        }
    }
    private Thread thread;
    private void startThread() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(1000);
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    Date now = new Date(System.currentTimeMillis());
                    if(now.compareTo(due) > 0) {
                        bundle.putString("msg", "TimerMessage");
                        msg.setData(bundle);
                    }


                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }
}
