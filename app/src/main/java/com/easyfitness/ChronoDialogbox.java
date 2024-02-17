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
    String strCurrentTime = "";
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
        Intent intent = new Intent(mContext,ChronoService.class);
        mContext.startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreateDialog");
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle(c.getResources().getString(R.string.ChronometerLabel)); //ChronometerLabel
        setContentView(R.layout.dialog_chrono);
        this.setCanceledOnTouchOutside(false); // make it modal

        startChronoService();
        ChronoService chronoService = ChronoService.getInstance();
        this.chronoService = chronoService;
        System.out.println("valor timing al crear un nuevo dialog "+ chronoService.getTiming_start());


        startstop = findViewById(R.id.btn_startstop);
        exit = findViewById(R.id.btn_exit);
        reset = findViewById(R.id.btn_reset);
        chrono = findViewById(R.id.chronoValue);

        startstop.setOnClickListener(this);
        exit.setOnClickListener(this);
        reset.setOnClickListener(this);
        /*
        startTime = SystemClock.elapsedRealtime();
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();
        startTime = SystemClock.elapsedRealtime();
        chronoStarted = true;

        startstop.setText("Stop");*/

        if(chronoService.getTiming_start() == 0){
            startTime = SystemClock.elapsedRealtime();
        }else{
            startTime = chronoService.getTiming_start();
            if( chronoService.getTiming_stop() == 0){
                stopTime = SystemClock.elapsedRealtime();
            }else{
                stopTime = chronoService.getTiming_stop();
            }
            startTime = SystemClock.elapsedRealtime() - (stopTime - startTime);
            chrono.setBase(startTime);
        }

        chrono.start();
        chronoStarted = true;
        startstop.setText("Stop");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startstop:
                if (chronoStarted) {
                    chrono.stop();
                    stopTime = SystemClock.elapsedRealtime();
                    chronoService.setTiming_stop(stopTime);
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
                    startstop.setText("Stop");
                }
                chronoResetted = false;
                break;
            case R.id.btn_reset:
                startTime = SystemClock.elapsedRealtime();
                chrono.setBase(startTime);
                chrono.setText("00:00:0");
                System.out.println("btn_reset---------------------------");
                chronoResetted = true;
                break;
            case R.id.btn_exit:
             /*   chrono.stop();
                chronoStarted = false;
                chrono.setText("00:00:0");
                startstop.setText("Start"); */
                chronoService.setTiming_start(startTime);
                chronoService.setTiming_stop(stopTime);
                dismiss();
                break;
            default:
                break;
        }
    }
}
