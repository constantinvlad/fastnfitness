package com.easyfitness;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ChronoService extends Service {
    private static ChronoService instance;

    boolean chronoStarted = false;
    long timing_start = 0;
    long timing_stop = 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }
    public long getTiming_start() {
        return timing_start;
    }
    public void setTiming_start(long timing_start) {
        this.timing_start = timing_start;
    }
    public long getTiming_stop() {
        return timing_stop;
    }
    public void setTiming_stop(long timing_stop) {
        this.timing_stop = timing_stop;
    }
    public void resetService(){
        this.timing_start = 0;
    }
    public static ChronoService getInstance() {
        if (instance == null) {
            instance = new ChronoService();
        }else{
        }
        return instance;
    }
    public boolean isChronoStarted() {
        return chronoStarted;
    }
    public void setChronoStarted(boolean chronoStarted) {
        this.chronoStarted = chronoStarted;
    }

}
