package com.example.thongle.multithreading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_message;
    private Button button_post;
    private Button button_asynctask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_asynctask = (Button) findViewById(R.id.btn_asynctask);
        button_post = (Button) findViewById(R.id.btn_post);
        button_message = (Button) findViewById(R.id.btn_message);

        button_asynctask.setOnClickListener(this);
        button_message.setOnClickListener(this);
        button_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_message:
                Intent intent1 = new Intent(MainActivity.this, HandlerMessageAcitivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_asynctask:
                Intent intent3 = new Intent(MainActivity.this, AsyncTaskAcitivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_post:
                Intent intent2 = new Intent(MainActivity.this, HandlerPostAcitivity.class);
                startActivity(intent2);
                break;
        }
    }
}
