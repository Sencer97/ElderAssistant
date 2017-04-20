package com.example.shengchun.elderassistant.util.address;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shengchun.elderassistant.R;

public class ChatActivity extends Activity {

    private ImageButton back_btn,send_btn,voice_btn,text_btn;
    private TextView contact_name;
    private EditText msg;
    private Button press_talk_btn;
    private FloatingActionButton floatingActionButton;     //语音视频浮动按钮
 //   private boolean isVoiceBtnPressed,isTextBtnPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        initView();
        initData();
    }

    private void initView() {
        back_btn = (ImageButton) findViewById(R.id.back_btn);
        voice_btn = (ImageButton) findViewById(R.id.ib_voice);
        text_btn = (ImageButton) findViewById(R.id.ib_text);
        send_btn = (ImageButton) findViewById(R.id.iv_send);
        msg = (EditText) findViewById(R.id.et_content);
        press_talk_btn = (Button) findViewById(R.id.press_talk);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

    }

    private void initData() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.back_btn:
                        onBackPressed();
                        break;
                    case R.id.iv_send:

                        break;
                    case R.id.ib_voice:
                        voice_btn.setVisibility(View.GONE);
                        msg.setVisibility(View.GONE);
                        send_btn.setVisibility(View.GONE);
                        press_talk_btn.setVisibility(View.VISIBLE);
                        text_btn.setVisibility(View.VISIBLE);
                        break;
                    case R.id.ib_text:
                        voice_btn.setVisibility(View.VISIBLE);
                        msg.setVisibility(View.VISIBLE);
                        send_btn.setVisibility(View.VISIBLE);
                        press_talk_btn.setVisibility(View.GONE);
                        text_btn.setVisibility(View.GONE);
                        break;
                    case R.id.fab:
                        break;
                }
            }
        };
        back_btn.setOnClickListener(clickListener);
        voice_btn.setOnClickListener(clickListener);
        send_btn.setOnClickListener(clickListener);
        text_btn.setOnClickListener(clickListener);
    }
}
