package com.example.wheeler.jamessummer2017finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by wheeler on 7/12/17.
 */

public class Task implements Parcelable {
    public String name;
    public String info;
    public double duration;
    public Date due;

    public void setName(String name) {
        this.name = name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Task(){
        name = "";
        info = "";

    }

    public Task(String name, String info){
        this.name = name;
        this.info = info;
    }

    public String getName(){
        return name;
    }

    public String getInfo(){
        return info;
    }


    protected Task(Parcel in) {
        name = in.readString();
        info = in.readString();
        duration = in.readDouble();
        long tmpDue = in.readLong();
        due = tmpDue != -1 ? new Date(tmpDue) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(info);
        dest.writeDouble(duration);
        dest.writeLong(due != null ? due.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}