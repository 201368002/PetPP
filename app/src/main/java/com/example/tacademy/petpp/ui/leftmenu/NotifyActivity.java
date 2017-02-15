package com.example.tacademy.petpp.ui.leftmenu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.base.BaseActivity;

import static com.example.tacademy.petpp.R.mipmap.btn_notice_off;
import static com.example.tacademy.petpp.R.mipmap.btn_notice_on;

public class NotifyActivity extends BaseActivity {

    // 상단바
    Button nextBtn;
    TextView title_text;

    ImageView notify_item1, notify_item2;
    LinearLayout notify_item_detail1, notify_item_detail2;
    Boolean item1_type = false;
    Boolean item2_type = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        // 상단바
        nextBtn = (Button)findViewById(R.id.nextBtn);
        nextBtn.setVisibility(View.INVISIBLE);
        title_text = (TextView)findViewById(R.id.title_text);
        title_text.setText("공지사항");

        notify_item1 = (ImageView)findViewById(R.id.notify_item1);
        notify_item_detail1 = (LinearLayout)findViewById(R.id.notify_item_detail1);
        notify_item2 = (ImageView)findViewById(R.id.notify_item2);
        notify_item_detail2 = (LinearLayout)findViewById(R.id.notify_item_detail2);

    }

    public void onNotifyItem1(View view){
        if(item1_type == false) {
            item1_type = true;
            notify_item1.setImageResource(btn_notice_off);
            notify_item_detail1.setVisibility(View.VISIBLE);
        }else{
            item1_type = false;
            notify_item1.setImageResource(btn_notice_on);
            notify_item_detail1.setVisibility(View.GONE);
        }
    }

    public void onNotifyItem2(View view){
        if(item2_type == false) {
            item2_type = true;
            notify_item2.setImageResource(btn_notice_off);
            notify_item_detail2.setVisibility(View.VISIBLE);
        }else{
            item2_type = false;
            notify_item2.setImageResource(btn_notice_on);
            notify_item_detail2.setVisibility(View.GONE);
        }
    }
}
