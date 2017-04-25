package com.example.thongle.multithreading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by thongle on 26/04/2017.
 */

public class HandlerMessageAcitivity extends AppCompatActivity{
    private ProgressBar progressBar_First, progressBar_Second;
    private TextView textView_Working, textView_Returned;
    private boolean isRunning;
    private int MAX_SEC = 20;
    private int intTest = 1;
    private Thread bgThread;
    private Handler handler;
    private Button button_Start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        progressBar_First = (ProgressBar) findViewById(R.id.prb_first);
        progressBar_Second = (ProgressBar) findViewById(R.id.prb_second);
        button_Start = (Button) findViewById(R.id.btn_start);
        textView_Returned = (TextView) findViewById(R.id.txtv_return);
        textView_Working = (TextView) findViewById(R.id.txtv_working);

        isRunning = false;
        progressBar_First.setMax(MAX_SEC);
        progressBar_First.setProgress(0);

        progressBar_First.setVisibility(View.GONE);
        progressBar_Second.setVisibility(View.GONE);

        button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = true;
                button_Start.setVisibility(View.GONE);
                progressBar_First.setVisibility(View.VISIBLE);
                progressBar_Second.setVisibility(View.VISIBLE);
                bgThread.start();
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                String returnedValue = (String) msg.obj;
                textView_Returned.setText(getString(R.string.returned_by_bg_thread) + returnedValue);
                progressBar_First.incrementProgressBy(2);

                if(progressBar_First.getProgress() == MAX_SEC){
                    textView_Returned.setText(getString(R.string.done_background_thread_has_been_stopped));
                    textView_Working.setText(getString(R.string.done));
                    progressBar_First.setVisibility(View.GONE);
                    progressBar_Second.setVisibility(View.GONE);
                    button_Start.setVisibility(View.VISIBLE);
                    isRunning = false;
                }
                else
                    textView_Working.setText(getString(R.string.working) + progressBar_First.getProgress());
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        bgThread = new Thread(){
            @Override
            public void run() {
                for(int i = 0; i < MAX_SEC && isRunning; i++){
                    try {
                        Thread.sleep(1000);

                        Random random = new Random();
                        String data = "Thread value: " + random.nextInt(101);
                        data += getString(R.string.global_value_seen) + " " + intTest;
                        intTest ++;
                        if(isRunning){
                            Message message = handler.obtainMessage(1, data);
                            handler.sendMessage(message);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
