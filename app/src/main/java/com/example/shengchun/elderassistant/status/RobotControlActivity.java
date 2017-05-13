package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;

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
   /* public OutputStream getOutputStream() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        int a2dp = adapter.getProfileConnectionState(BluetoothProfile.A2DP);           //可操控蓝牙设备，带播放暂停的蓝牙耳机
        int headset = adapter.getProfileConnectionState(BluetoothProfile.HEADSET);      //头戴式耳机，支持语音输入输出
        int health = adapter.getProfileConnectionState(BluetoothProfile.HEALTH);         //蓝牙穿戴式设备
        String address = "";        //E0:94:67:75:19:66电脑蓝牙mac地址
        try {       //通过反射机制，得到连接蓝牙的地址
            Method method = BluetoothAdapter.class.getDeclaredMethod("getConnectionState", (Class[]) null);
            //打开权限
            method.setAccessible(true);
            int state = (int) method.invoke(adapter, (Object[]) null);
       //     if(state == BluetoothAdapter.STATE_CONNECTED){
                Log.e(TAG,"BluetoothAdapter.STATE_CONNECTED");
                Set<BluetoothDevice> devices = adapter.getBondedDevices();
                Log.e(TAG,"devices:"+devices.size());
                for(BluetoothDevice device : devices){
                    Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
                    method.setAccessible(true);
                    boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
                    if(isConnected){
                        Log.e(TAG,"connected:"+device.getAddress());
                        address = device.getAddress();
                    }
                }
        //    }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(address.equals("")){
            Toast.makeText(getBaseContext(),"请先连接蓝牙设备~.~",Toast.LENGTH_SHORT).show();
        }else{
            try {
                device = adapter.getRemoteDevice(address);
                socket = device.createRfcommSocketToServiceRecord(My_UUID);
                socket.connect();
                outputStream = socket.getOutputStream();
                Log.e(TAG, "getOutputStream: "+outputStream);
                outputStream.write('W');
                Log.e(TAG, "getOutputStream: "+outputStream);

            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
        return outputStream;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_robot_control);
        init();

    }       /**
             *  写数据
             * @param  s 要写入的字符串
             */
    public void writeData(String s){
                try{
                    byte[] buffer = s.getBytes();
                    if(BLEConnectThread.bluetoothSocket==null){
                        Toast.makeText(getBaseContext(),"请先连接蓝牙设备~",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        os =  BLEConnectThread.bluetoothSocket.getOutputStream();             //getOutputStream();
                        Log.e(TAG, "run: 往" +s.toString() +"走");
                        os.write(buffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG,e.toString());
                }
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
        int selectColor = getResources().getColor(R.color.blue_btn_bg_pressed_color);
        RoundControlView.RoundMenu roundMenuUp = new RoundControlView.RoundMenu();
        roundMenuUp.selectSolidColor = selectColor;
        roundMenuUp.strokeColor = selectColor;
        roundMenuUp.icon = BitmapFactory.decodeResource(getResources(), icon_dir);
        roundMenuUp.onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO robot go ahead
                writeData("W");
                Toast.makeText(getBaseContext(),"往前走",Toast.LENGTH_SHORT).show();
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
                writeData("S");
                Toast.makeText(getBaseContext(),"往后走",Toast.LENGTH_SHORT).show();
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
                writeData("D");
                Toast.makeText(getBaseContext(),"往右走",Toast.LENGTH_SHORT).show();
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
                writeData("A");
                Toast.makeText(getBaseContext(),"往左走",Toast.LENGTH_SHORT).show();
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


                        Toast.makeText(getBaseContext(),"Stop",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
