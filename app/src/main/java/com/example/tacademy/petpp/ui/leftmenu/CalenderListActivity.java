package com.example.tacademy.petpp.ui.leftmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;

public class CalenderListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalenderListActivity.this, CalWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}
