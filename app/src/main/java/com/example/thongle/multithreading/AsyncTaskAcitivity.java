package com.example.thongle.multithreading;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by thongle on 26/04/2017.
 */

public class AsyncTaskAcitivity extends AppCompatActivity {

    private Button button_QuickJob, button_SlowJob;
    private TextView textView_Status;
    private SlowTask slowTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        button_QuickJob = (Button) findViewById(R.id.btn_quick_job);
        button_SlowJob = (Button) findViewById(R.id.btn_slow_job);
        textView_Status = (TextView) findViewById(R.id.txtv_status);

        slowTask = new SlowTask(AsyncTaskAcitivity.this, textView_Status);

        button_QuickJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                textView_Status.setText(sdf.format(new Date()));
            }
        });
        button_SlowJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slowTask.execute();
            }
        });
    }
}
