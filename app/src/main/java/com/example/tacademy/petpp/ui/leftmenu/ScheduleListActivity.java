package com.example.tacademy.petpp.ui.leftmenu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;
import com.example.tacademy.petpp.util.Log;

public class ScheduleListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.getInstance().log("산책 예약 목록!!!!");
            }
        });

    }
}
