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
import android.widget.Button;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;
import com.suke.widget.SwitchButton;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static com.example.shengchun.elderassistant.R.drawable.stop;

        /**
         * 遥控机器人
         *
         */

public class RobotControlActivity extends Activity {
    private Toolbar toolbar;
    private RoundControlView roundControlView;            //遥控方向盘
    private SwitchButton car_light;
    private Button start_btn,stop_btn,half_auto_btn,control_btn;
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

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.start_btn:
                    writeData("R@#");
//                    startRobot();
                    break;
                case R.id.stop_btn:
                    writeData("T@#");
//                    stopRobot();
                    break;
                case R.id.half_auto_btn:
                    writeData("O@#");
//                    halfAutoMode();
                    break;
                case R.id.hand_control_btn:
                    writeData("P@#");
//                    handControlMode();
                    break;

            }
        }
    };
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
        start_btn = (Button) findViewById(R.id.start_btn);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        half_auto_btn = (Button) findViewById(R.id.half_auto_btn);
        control_btn = (Button) findViewById(R.id.hand_control_btn);
        start_btn.setOnClickListener(clickListener);
        stop_btn.setOnClickListener(clickListener);
        half_auto_btn.setOnClickListener(clickListener);
        control_btn.setOnClickListener(clickListener);
        //车灯控制
        car_light.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked){
                    //turn on the light
                    writeData("K@#");
//                    turnOnLight();
                }else {
                    //turn off the light
                    writeData("G@#");
//                    turnOffLight();
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
//                turnForward();
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
//                turnBack();
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
//                turnRight();
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
//                turnOnLight();
            }
        };

        //添加四个方向的按钮            从下开始顺时针
        roundControlView.addRoundMenu(roundMenuDown);
        roundControlView.addRoundMenu(roundMenuLeft);
        roundControlView.addRoundMenu(roundMenuUp);
        roundControlView.addRoundMenu(roundMenuRight);
        roundControlView.setCoreMenu(selectColor,
                getResources().getColor(R.color.red_btn_bg_color),selectColor
                , 1, 0.43,BitmapFactory.decodeResource(getResources(), stop), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        writeData("Q@#");
//                        stop();
                    }
                });
    }
    public static void turnForward(){
        String address = "";
        String operation = "向前";
        address = "http://119.23.8.34/i_robot/forward.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void turnBack(){
                String address = "";
                String operation = "向后";
                address = "http://119.23.8.34/i_robot/back.php";
                SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
                sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void turnLeft(){
                String address = "";
                String operation = "向左";
                address = "http://119.23.8.34/i_robot/left.php";
                SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
                sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void turnRight(){
                String address = "";
                String operation = "向后";
                address = "http://119.23.8.34/i_robot/right.php";
                SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
                sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void stop(){
                String address = "";
                String operation = "停止";
                address = "http://119.23.8.34/i_robot/stop.php";
                SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
                sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void turnOnLight(){
                String address = "";
                String operation = "开车灯";
                address = "http://119.23.8.34/i_robot/kd.php";
                SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
                sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void turnOffLight(){
                String address = "";
                String operation = "关车灯";
                address = "http://119.23.8.34/i_robot/gd.php";
                SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
                sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void startRobot(){
        String address = "";
        String operation = "启动小车";
        address = "http://119.23.8.34/i_robot/kc.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void stopRobot(){
        String address = "";
        String operation = "关车灯";
        address = "http://119.23.8.34/i_robot/gc.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void halfAutoMode(){
        String address = "";
        String operation = "半自动";
        address = "http://119.23.8.34/i_robot/bzd.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    public static void handControlMode(){
        String address = "";
        String operation = "手控";
        address = "http://119.23.8.34/i_robot/sk.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }


}
