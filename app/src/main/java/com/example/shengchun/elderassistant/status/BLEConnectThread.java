package com.example.shengchun.elderassistant.status;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author Sencer
 * @create 2017/5/3
 * @vertion 1.0
 * @description
 */

public class BLEConnectThread extends Thread {
    /**
     * 蓝牙连接线程
     */
        String macAddress = "";
        long connectTime;
        private boolean conntected;
        private boolean connecting;
        private android.bluetooth.BluetoothDevice device;
        public static BluetoothSocket bluetoothSocket;
        private BluetoothAdapter bluetoothAdapter;
        private final UUID My_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        private static final String TAG = "BLEConnectThread";
        public static boolean isConntected = true;

    public BLEConnectThread(String mac ,BluetoothAdapter bluetoothAdapter) {
            this.macAddress = mac;
            this.bluetoothAdapter = bluetoothAdapter;
        }
        public void run() {
            connecting = true;
            conntected = false;
            device = bluetoothAdapter.getRemoteDevice(macAddress);
            if (bluetoothAdapter == null) {
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            }
            bluetoothAdapter.cancelDiscovery();     //连接设备之前就取消搜索设备

            if(!conntected){
                conntectDevice();
            }
            Log.e(TAG, "test aaaaa");
            while (!conntected && connectTime <= 10000) {         //设置连接时间
                conntectDevice();
            }
        }
    public void conntectDevice() {
        try {
            if (device.getBondState() == android.bluetooth.BluetoothDevice.BOND_NONE) {
                Method creMethod = android.bluetooth.BluetoothDevice.class.getMethod("createBond");
                Log.e(TAG, "conntectDevice: 开始配对！" );
//                    Toast.makeText(getContext(),"即将尝试配对...",Toast.LENGTH_SHORT).show();
                creMethod.invoke(device);
            }else{
                Log.e(TAG, "conntectDevice: 已和该设备配对！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        bluetoothAdapter.cancelDiscovery();
        try{
            bluetoothSocket = device.createRfcommSocketToServiceRecord(My_UUID);
            bluetoothSocket.connect();
            conntected = true;
            Log.e(TAG, "conntectDevice: 连接成功！");
//                Toast.makeText(getContext(),"连接成功！",Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            connectTime++;
            conntected = false;

            try{
                bluetoothSocket.close();
                bluetoothSocket = null;
            }catch (IOException e1){
                Log.e(TAG, "conntectDevice: 连接失败!");
            }
        }
        finally {
            connecting = false;
        }
    }

}

