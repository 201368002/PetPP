package com.example.tacademy.petpp.ui.leftmenu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;

public class HelperActivity extends BaseActivity {

    Button nextBtn;
    TextView title_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);

        // 상단바
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setVisibility(View.INVISIBLE);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("고객센터/도움말");

    }
}
