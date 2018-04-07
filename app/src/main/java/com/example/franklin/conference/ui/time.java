package com.example.franklin.conference.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franklin.conference.App.MyApplication;
import com.example.franklin.conference.R;

import org.feezu.liuli.timeselector.TimeSelector;

public class time extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        textView = (TextView) findViewById(R.id.textView2);


        final TimeSelector timeSelector = new TimeSelector(time.this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
               textView.setText(time);
                Toast.makeText(time.this, time, Toast.LENGTH_SHORT).show();
            }
        }, "2015-01-01 00:00", "2018-12-31 23:59:59");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSelector.show();
            }
        });


    }




}
