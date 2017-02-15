package com.example.tacademy.petpp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tacademy.petpp.R;

public class DetailPostActivity extends AppCompatActivity {

    Button nextBtn;
    TextView title_text, detailMainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        // 상단바
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setVisibility(View.INVISIBLE);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("게시물 보기");

        detailMainText = (TextView)findViewById(R.id.detailMainText);
        detailMainText.setMovementMethod(new ScrollingMovementMethod());

    }
}
