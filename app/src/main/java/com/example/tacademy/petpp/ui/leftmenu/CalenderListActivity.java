package com.example.tacademy.petpp.ui.leftmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;

public class CalenderListActivity extends BaseActivity {

    // 상단바
    Button nextBtn;
    TextView title_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_list);

        // 상단바
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setVisibility(View.INVISIBLE);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("시터 관리");

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
