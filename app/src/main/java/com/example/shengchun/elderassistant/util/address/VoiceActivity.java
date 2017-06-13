package com.example.shengchun.elderassistant.util.address;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;


public class VoiceActivity extends Activity {
    private Toolbar toolbar;
    private ImageButton speak;
    private ListView lv;
    private ArrayAdapter adapter;
    private int count=0;
    String[] text = {"刚刚风太大了，没听清~","帮我拿本书","请再说一遍~","帮我拿本书","谢谢"};
    private static final String TAG = "VoiceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        init();
        Log.e(TAG, "onCreate: ");
    }

    private void init() {
        Toast.makeText(getBaseContext(),"请点击下方语音按钮并开始说话~",Toast.LENGTH_SHORT).show();
        toolbar = (Toolbar) findViewById(R.id.voice_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new ArrayAdapter<String>(getBaseContext(),R.layout.devices_item, R.id.device_name);
        lv = (ListView) findViewById(R.id.lv_text);
        lv.setAdapter(adapter);
        speak = (ImageButton) findViewById(R.id.btn_speak);

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(speak,"倾听ing~",Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.add(text[count]);
                        adapter.notifyDataSetChanged();
                        count++;
                        count = (count>4)?count-5:count;
                    }
                }, 3000);

            }

        });

    }

}
