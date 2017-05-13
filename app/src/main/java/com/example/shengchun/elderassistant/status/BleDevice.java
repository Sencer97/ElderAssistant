package com.example.shengchun.elderassistant.status;

/**
 * @author Sencer
 * @create 2017/5/3
 * @vertion 1.0
 * @description   蓝牙设备类
 */

public class BleDevice {
        private String name;
        private String macAddress;

        public BleDevice(String name, String address){
            this.name = name;
            this.macAddress = address;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }
}

