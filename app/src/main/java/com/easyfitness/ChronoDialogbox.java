package com.easyfitness;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import gr.antoniom.chronometer.Chronometer;

public class ChronoDialogbox extends Dialog implements
        android.view.View.OnClickListener {
    public ChronoService chronoService;
    public Activity c;
    public Dialog d;
    public Button startstop, exit, reset;
    public Chronometer chrono;
    long startTime = 0;
    long stopTime = 0;
    private boolean chronoStarted = false;
    private boolean chronoResetted = false;
    private Context mContext; // Asegúrate de tener una referencia al contexto

    public ChronoDialogbox(Activity a) {
        super(a);
        this.c = a;
    }

    private void startChronoService() {
        mContext = getContext();
        Intent intent = new Intent(mContext, ChronoService.class);
        mContext.startService(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle(c.getResources().getString(R.string.ChronometerLabel)); //ChronometerLabel
        setContentView(R.layout.dialog_chrono);
        this.setCanceledOnTouchOutside(false); // make it modal

        startChronoService();
        ChronoService chronoService = ChronoService.getInstance();
        this.chronoService = chronoService;


        startstop = findViewById(R.id.btn_startstop);
        exit = findViewById(R.id.btn_exit);
        reset = findViewById(R.id.btn_reset);
        chrono = findViewById(R.id.chronoValue);

        startstop.setOnClickListener(this);
        exit.setOnClickListener(this);
        reset.setOnClickListener(this);

        if(chronoService.getTiming_start() == 0){//Instrancia chronoService 1º vez
            chrono.setBase(SystemClock.elapsedRealtime());
            chrono.start();
            startTime = SystemClock.elapsedRealtime();
            chronoStarted = true;
            startstop.setText("Stop");
        }else{
            if(chronoService.isChronoStarted()){//se dejo en Stop
                startTime = chronoService.getTiming_start();
                stopTime = chronoService.getTiming_stop();
                chrono.setBase(SystemClock.elapsedRealtime() - (stopTime - startTime));
                chrono.stop();
                chronoStarted = false;
                startstop.setText("Start");
            }else{//se dejo en Start
                chrono.setBase(chronoService.getTiming_start());
                chrono.start();
                startTime = chronoService.getTiming_start();
                chronoStarted = true;
                startstop.setText("Stop");
            }
        }
    }

        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startstop:
                if (chronoStarted) {
                    chrono.stop();
                    stopTime = SystemClock.elapsedRealtime();
                    chronoService.setChronoStarted(true);
                    chronoStarted = false;
                    startstop.setText("Start");
                } else {
                    if (chronoResetted) {
                        startTime = SystemClock.elapsedRealtime();
                    } else {
                        startTime = SystemClock.elapsedRealtime() - (stopTime - startTime);
                    }
                    chrono.setBase(startTime);
                    chrono.start();
                    chronoStarted = true;
                    chronoService.setChronoStarted(false);
                    startstop.setText("Stop");
                }
                chronoResetted = false;
                break;
            case R.id.btn_reset:
                startTime = SystemClock.elapsedRealtime();
                chrono.setBase(startTime);
                chrono.setText("00:00:0");
                chronoResetted = true;
                break;
            case R.id.btn_exit:
                chronoService.setTiming_start(startTime);
                chronoService.setTiming_stop(stopTime);
                dismiss();
                break;
        }
    }
}
