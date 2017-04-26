package com.example.thongle.multithreading;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by thongle on 26/04/2017.
 */

public class HandlerPostAcitivity extends AppCompatActivity {
    private final String PATIENCE = "Some important data is being collected now.\nPlease be patient...wait...";
    private ProgressBar progressBar_Waiting;
    private TextView textView_TopCaption;
    private EditText editText_Input;
    private Button button_Execute;
    private int globalValue, accum;
    private long startTime;
    private Handler handler;
    private Runnable fgRunnable, bgRunnable;
    private Thread testThread;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        textView_TopCaption = (TextView) findViewById(R.id.txtv_top_caption);
        progressBar_Waiting = (ProgressBar) findViewById(R.id.prb_waiting);
        editText_Input = (EditText) findViewById(R.id.edtxt_input);
        button_Execute = (Button) findViewById(R.id.btn_execute);

        startTime = System.currentTimeMillis();
        globalValue = 0;
        accum = 0;
        handler = new Handler();

        fgRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int progress_step = 5;
                    double total_time = (System.currentTimeMillis() - startTime) / 1000;
                    synchronized (this) {
                        globalValue += 100;
                    }
                    textView_TopCaption.setText(PATIENCE + total_time + " - " + globalValue);
                    progressBar_Waiting.incrementProgressBy(progress_step);
                    accum += progress_step;

                    if (accum >= progressBar_Waiting.getMax()) {
                        textView_TopCaption.setText(getString(R.string.bg_work_is_over));
                        progressBar_Waiting.setVisibility(View.GONE);
                    }
                }
                catch (Exception e){}
            }
        };
        bgRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < 20; i++){
                        Thread.sleep(1000);
                        synchronized (this){
                            globalValue += 1;
                        }
                        handler.post(fgRunnable);
                    }
                }
                catch (Exception e){}
            }
        };
        testThread = new Thread(bgRunnable);

        button_Execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText_Input.getText().toString();
                Toast.makeText(HandlerPostAcitivity.this, "You said: " + input, Toast.LENGTH_SHORT).show();
            }
        });
        testThread.start();
        progressBar_Waiting.incrementProgressBy(0);
    }
}
