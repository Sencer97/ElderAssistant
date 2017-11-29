package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.shengchun.elderassistant.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.suke.widget.SwitchButton;

import java.io.OutputStream;

public class AppliancesActivity extends Activity {
    private Toolbar toolbar;
    private SwitchButton sb_tv, sb_air, sb_light, sb_door, sb_window, sb_curtain, sb_lock;
    private OutputStream os;
    private static final String TAG = "AppliancesActivity";
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.appliances_toolbar);
        sb_air = (SwitchButton) findViewById(R.id.sb_air);
        sb_light = (SwitchButton) findViewById(R.id.sb_light);
        sb_tv = (SwitchButton) findViewById(R.id.sb_tv);
        sb_door = (SwitchButton) findViewById(R.id.sb_door);
        sb_window = (SwitchButton) findViewById(R.id.sb_window);
        sb_curtain = (SwitchButton) findViewById(R.id.sb_curtain);
        sb_lock = (SwitchButton) findViewById(R.id.sb_lock);
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
                switch (view.getId()) {
                    case R.id.sb_air:
                        if (isChecked) {
                            //turn on the air
                            openAirConditioning();
//                            Snackbar.make(sb_air,"空调已开~",Snackbar.LENGTH_SHORT).show();
                        } else {
                            //turn off the air
                            closeAirConditioning();
//                            Snackbar.make(sb_air,"空调已关~",Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.sb_tv:
                        if (isChecked) {
                            //turn on the TV
                            openTelevision();
//                            Snackbar.make(sb_tv,"电视已开~",Snackbar.LENGTH_SHORT).show();
                        } else {
                            //turn off the TV
                            closeTelevision();
//                            Snackbar.make(sb_tv,"电视已关~",Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.sb_light:
                        if (isChecked) {
                            //turn on the light
                            openGuestRoomBulb();
//                            Snackbar.make(sb_light,"电灯已开~",Snackbar.LENGTH_SHORT).show();
                        } else {
                            //turn off the light
                            closeGuestRoomBulb();
//                            Snackbar.make(sb_light,"电灯已关~",Snackbar.LENGTH_SHORT).show();

                        }
                        break;
                    case R.id.sb_door:
                        if (isChecked) {
                            openDoor();
//                            Snackbar.make(sb_door,"房门已开~",Snackbar.LENGTH_SHORT).show();
                        } else {
                            closeDoor();
//                            Snackbar.make(sb_door,"房门已关~",Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.sb_window:
                        if (isChecked) {
                            openWindow();
//                            Snackbar.make(sb_window,"窗户已开~",Snackbar.LENGTH_SHORT).show();
                        } else {
                            closeWindow();
//                            Snackbar.make(sb_window,"窗户已关~",Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.sb_curtain:
                        if (isChecked) {
                            openCurtain();
//                            Snackbar.make(sb_window,"窗户已开~",Snackbar.LENGTH_SHORT).show();
                        } else {
                            closeCurtain();
//                            Snackbar.make(sb_window,"窗户已关~",Snackbar.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.sb_lock:
                        if (isChecked) {
                            openHearter();
                        } else {
                            closeHearter();
                        }
                        break;
                }
            }
        };
        sb_air.setOnCheckedChangeListener(checkedChangeListener);
        sb_light.setOnCheckedChangeListener(checkedChangeListener);
        sb_tv.setOnCheckedChangeListener(checkedChangeListener);
        sb_door.setOnCheckedChangeListener(checkedChangeListener);
        sb_window.setOnCheckedChangeListener(checkedChangeListener);
        sb_curtain.setOnCheckedChangeListener(checkedChangeListener);
        sb_lock.setOnCheckedChangeListener(checkedChangeListener);
    }

    /**
     * 打开热水器
     */
    public static void openHearter() {

        String address = "";
        String operation = "开锁";
        address = "http://119.23.8.34/openwrt/ks.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    /**
     * 关闭热水器
     */
    public static void closeHearter() {

        String address = "";
        String operation = "关锁";
        address = "http://119.23.8.34/openwrt/gs.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }
    /**
     * 打开窗帘
     */
    public static void openCurtain() {
        String address = "";
        String operation = "开窗帘";
        address = "http://119.23.8.34/openwrt/kcl.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 关窗帘
     */
    public static void closeCurtain() {

        String address = "";
        String operation = "关窗帘";
        address = "http://119.23.8.34/openwrt/gcl.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 打开客厅灯
     */
    public static void openGuestRoomBulb() {

        String address = "";
        String operation = "开客厅灯";
        address = "http://119.23.8.34/openwrt/kkt.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 关闭客厅灯
     */
    public static void closeGuestRoomBulb() {

        String address = "";
        String operation = "关客厅灯";
        address = "http://119.23.8.34/openwrt/gkt.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 打开空调
     */
    public static void openAirConditioning() {

        String address = "";
        String operation = "开空调";
        address = "http://119.23.8.34/openwrt/kfs.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 关闭空调
     */
    public static void closeAirConditioning() {

        String address = "";
        String operation = "关空调";
        address = "http://119.23.8.34/openwrt/gfs.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();

    }

    /**
     * 打开电视
     */
    public static void openTelevision() {

        String address = "";
        // Toast.makeText(MyApplication.getContext(), "电视已开", Toast.LENGTH_SHORT).show();
        String operation = "开电视";
        address = "http://119.23.8.34/openwrt/kds.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 关闭电视
     */
    public static void closeTelevision() {
        String address = "";
        String operation = "关电视";
        address = "http://119.23.8.34/openwrt/gds.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 关闭门
     */
    public static void closeDoor() {

        String address = "";
        String operation = "关门";
        address = "http://119.23.8.34/openwrt/gm.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 打开门
     */
    public static void  openDoor() {
        String address = "";
        address = "http://119.23.8.34/openwrt/km.php";
        String operation = "开门";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 关闭窗
     */
    public static void closeWindow() {

        String address = "";
        String operation = "关窗";
        address = "http://119.23.8.34/openwrt/gc.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }

    /**
     * 打开窗
     */
    public static void openWindow() {
        String address = "";
        String operation = "开窗";
        address = "http://119.23.8.34/openwrt/kc.php";
        SendRequestWithHttpUrlConnection sendRequestWithHttpUrlConnection = new SendRequestWithHttpUrlConnection(address, operation);
        sendRequestWithHttpUrlConnection.RequestInternetConnection();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Appliances Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
