package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.shengchun.elderassistant.R;
import com.suke.widget.SwitchButton;

import java.io.IOException;
import java.io.OutputStream;

public class AppliancesActivity extends Activity {
    private Toolbar toolbar;
    private SwitchButton sb_tv,sb_air,sb_light,sb_door;
    private OutputStream os;
    private static final String TAG = "AppliancesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        init();
    }
    /**  写数据
     *  @param  s 要写入的字符串
     */
    public void writeData(String s){
        final String data = s;
        new Thread(){
            @Override
            public void run() {
                try{
                    byte[] buffer = data.getBytes();
                    if(BLEConnectThread.bluetoothSocket==null){
                        finish();
                    }else{
                        os =  BLEConnectThread.bluetoothSocket.getOutputStream();             //getOutputStream();
                        os.write(buffer);
                        Log.e(TAG, "run: 开关灯~");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,e.toString());
                }
            }
        }.start();
    }
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.appliances_toolbar);
        sb_air = (SwitchButton) findViewById(R.id.sb_air);
        sb_light = (SwitchButton) findViewById(R.id.sb_light);
        sb_tv = (SwitchButton) findViewById(R.id.sb_tv);
        sb_door = (SwitchButton) findViewById(R.id.sb_door);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SwitchButton.OnCheckedChangeListener checkedChangeListener = new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                switch (view.getId()){
                    case R.id.sb_air:
                        if (isChecked){
                            //turn on the air

                            Snackbar.make(sb_air,"空调已开~",Snackbar.LENGTH_SHORT).show();
                        }else {
                            //turn off the air

                            Snackbar.make(sb_air,"空调已关~",Snackbar.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.sb_tv:
                        if (isChecked){
                            //turn on the TV

                            Snackbar.make(sb_tv,"电视已开~",Snackbar.LENGTH_SHORT).show();
                        }else {
                            //turn off the TV

                            Snackbar.make(sb_tv,"电视已关~",Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.sb_light:
                        if (isChecked){
                            //turn on the light

                            Snackbar.make(sb_light,"电灯已开~",Snackbar.LENGTH_SHORT).show();
                        }else {
                            //turn off the light
                            Snackbar.make(sb_light,"电灯已关~",Snackbar.LENGTH_SHORT).show();

                        }
                        break;
                    case R.id.sb_door:
                        if(isChecked){

                            Snackbar.make(sb_light,"房门已开~",Snackbar.LENGTH_SHORT).show();
                        }else {
                            Snackbar.make(sb_light,"房门已关~",Snackbar.LENGTH_SHORT).show();
                        }
                }
            }
        };
        sb_air.setOnCheckedChangeListener(checkedChangeListener);
        sb_light.setOnCheckedChangeListener(checkedChangeListener);
        sb_tv.setOnCheckedChangeListener(checkedChangeListener);

    }
}
