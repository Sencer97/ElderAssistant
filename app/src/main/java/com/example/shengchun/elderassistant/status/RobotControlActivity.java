package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;
import com.suke.widget.SwitchButton;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

        /**
         * 遥控机器人
         *
         */

public class RobotControlActivity extends Activity {
    private Toolbar toolbar;
    private RoundControlView roundControlView;            //遥控方向盘
    private SwitchButton car_light;
    private final int icon_dir = R.drawable.go;
    private static final String TAG = "RobotControlActivity";
    /**
     *   蓝牙相关变量
     */
    private final UUID My_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private android.bluetooth.BluetoothDevice device;
    private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothSocket socket;
    private OutputStream os;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_robot_control);
        init();

    }
    /**
             *  写数据
             * @param  s 要写入的字符串
             */
    public void writeData(String s){
            final String data = s;
            new Thread(){
                @Override
                public void run() {
                    try{
                        byte[] buffer = data.getBytes();
                        if(BLEConnectThread.bluetoothSocket==null){
                            Looper.prepare();
                           Toast.makeText(getBaseContext(),"请先连接设备~",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                         //  finish();
                        }else{
                            os =  BLEConnectThread.bluetoothSocket.getOutputStream();             //getOutputStream();
                            Log.e(TAG, "run: 往" +data.toString() +"走a");
                            os.write(buffer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG,e.toString());
                    }
                }
            }.start();
    }
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.robot_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        roundControlView = (RoundControlView) findViewById(R.id.roundControlView);
        car_light = (SwitchButton) findViewById(R.id.car_light_switch);
        //车灯控制
        car_light.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    //turn on the light
                    writeData("K@#");
                }else {
                    //turn off the light
                    writeData("G@#");
                }
            }
        });


        //遥控的方向盘
        int selectColor = getResources().getColor(R.color.blue_btn_bg_pressed_color);
        RoundControlView.RoundMenu roundMenuUp = new RoundControlView.RoundMenu();
        roundMenuUp.selectSolidColor = selectColor;
        roundMenuUp.strokeColor = selectColor;
        roundMenuUp.icon = BitmapFactory.decodeResource(getResources(), icon_dir);
        roundMenuUp.onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO robot go ahead
                writeData("W@#");
//                Toast.makeText(getBaseContext(),"往前走",Toast.LENGTH_SHORT).show();
            }
        };

        RoundControlView.RoundMenu roundMenuDown = new RoundControlView.RoundMenu();
        roundMenuDown.selectSolidColor = selectColor;
        roundMenuDown.strokeColor = selectColor;
        roundMenuDown.icon = BitmapFactory.decodeResource(getResources(),icon_dir);
        roundMenuDown.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO robot turn back
                writeData("S@#");
//                Toast.makeText(getBaseContext(),"往后走",Toast.LENGTH_SHORT).show();
            }
        };

        RoundControlView.RoundMenu roundMenuRight = new RoundControlView.RoundMenu();
        roundMenuRight.selectSolidColor = selectColor;
        roundMenuRight.strokeColor = selectColor;
        roundMenuRight.icon = BitmapFactory.decodeResource(getResources(),icon_dir);
        roundMenuRight.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO robot Turn right
                writeData("D@#");
//                Toast.makeText(getBaseContext(),"往右走",Toast.LENGTH_SHORT).show();
            }
        };

        RoundControlView.RoundMenu roundMenuLeft = new RoundControlView.RoundMenu();
        roundMenuLeft.selectSolidColor = selectColor;
        roundMenuLeft.strokeColor = selectColor;
        roundMenuLeft.icon = BitmapFactory.decodeResource(getResources(),icon_dir);
        roundMenuLeft.onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO robot turn left
                writeData("A@#");
//                Toast.makeText(getBaseContext(),"往左走",Toast.LENGTH_SHORT).show();
            }
        };

        //添加四个方向的按钮            从下开始顺时针
        roundControlView.addRoundMenu(roundMenuDown);
        roundControlView.addRoundMenu(roundMenuLeft);
        roundControlView.addRoundMenu(roundMenuUp);
        roundControlView.addRoundMenu(roundMenuRight);
        roundControlView.setCoreMenu(selectColor,
                getResources().getColor(R.color.red_btn_bg_color),selectColor
                , 1, 0.43,BitmapFactory.decodeResource(getResources(),R.drawable.stop), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        writeData("Q@#");
//                        Toast.makeText(getBaseContext(),"Stop",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
