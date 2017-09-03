package com.example.shengchun.elderassistant.status;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.shengchun.elderassistant.R.id.query;

/**
 * @author Sencer
 * @create 2017/3/19
 * @vertion 1.0
 * @description
 */

public class RobotFragment extends Fragment {
    private static final int REQUEST_ENABLE_BT = 1;
    private Context context;
    private ViewGroup viewGroup;
    private int[] gridIcons = {R.drawable.robot_control, R.drawable.environment, R.drawable.light, R.drawable.monitoring};
    private String[] gridText = {"遥控机器", "环境监控", "家电控制", "远程监视"};
    private GridView gridView;
    private RelativeLayout relativeLayout;
    private LoadToast myToast;
    private BluetoothAdapter bluetoothAdapter;
    private ListView connDevices, discoveredDevices;   //连接设备列表、发现的设备列表
    private List connList, discoverList;
    private ArrayAdapter listAdapter;      //已配对设备
    private ArrayAdapter discAdapter;
    private boolean isRegister = false;

    private Set<BluetoothDevice> pairedDevices;
    private static final String TAG = "RobotFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        viewGroup = (ViewGroup) inflater.inflate(R.layout.robot_fragment, container, false);
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myToast != null) {
                    myToast.error();
                }
                //  myToast.success();
            }
        });
        init();

        return viewGroup;
    }

    /**
     * gridView的适配器
     */
    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return gridIcons.length;
        }

        @Override
        public Object getItem(int position) {
            return gridIcons[position];
        }

        @Override
        public long getItemId(int position) {
            return gridIcons[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridHolder holder;
            if (convertView == null) {
                holder = new GridHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, null);
                holder.icon = (ImageView) convertView.findViewById(R.id.iv_item);
                holder.text = (TextView) convertView.findViewById(R.id.tv_item);       //注意是要convertView 来findView！！！
                convertView.setTag(holder);
            } else {
                holder = (GridHolder) convertView.getTag();
            }
            holder.icon.setImageResource(gridIcons[position]);
            holder.text.setText(gridText[position]);
            return convertView;
        }
    };

    /**
     * gridView 点击事件
     */
    AdapterView.OnItemClickListener gridItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:         //进入遥控机器界面
                    startActivity(new Intent(context, RobotControlActivity.class));
                    break;
                case 1:         //进入环境监控界面
                    startActivity(new Intent(context, EnvironmentActivity.class));
                    break;
                case 2:         //进入家电控制界面
                    startActivity(new Intent(context, AppliancesActivity.class));
                    break;
                case 3:         //进入远程监控界面
                    startActivity(new Intent(context, MonitoringActivity.class));
                //  startActivity(new Intent(context,YunDB.class));
                    break;
            }
        }
    };

    /**
     * 搜索蓝牙设备的广播
     */
    private final BroadcastReceiver searchReceiver = new BroadcastReceiver() {        //搜索蓝牙设备的广播
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = null;
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {       //发现设备
                myToast.success();
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Toast.makeText(context, device.getName()+"",Toast.LENGTH_SHORT).show();
                Log.d(TAG, device.getName());
                String str = device.getName() + "\n"
                        + device.getAddress();
                if (discoverList.indexOf(str) == -1)// 防止重复添加        //分行显示
                    discAdapter.add(str);
                if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    bluetoothAdapter.cancelDiscovery();
                }
                listAdapter.notifyDataSetChanged();
            }else{
                myToast.error();
                Toast.makeText(getContext(),"附近没有可用设备~,~",Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 活动销毁时，注销搜索蓝牙的广播
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isRegister){
            context.unregisterReceiver(searchReceiver);
            isRegister = false;
        }
    }

    private void init() {
        gridView = (GridView) viewGroup.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(gridItemClick);
        relativeLayout = (RelativeLayout) viewGroup.findViewById(query);
        discoverList = new ArrayList<String>();
        connList = new ArrayList<String>();
        connDevices = (ListView) viewGroup.findViewById(R.id.paired_devices);
        discoveredDevices = (ListView) viewGroup.findViewById(R.id.lv_device_discover);

        listAdapter = new ArrayAdapter<String>(context, R.layout.devices_item, R.id.device_name);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() == 0) {
            //   connDevices.setVisibility(View.GONE);
            listAdapter.add("None");
        }
        if (pairedDevices.size() > 0) {
            //    connDevices.setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                listAdapter.add(device.getName());           //显示手机已配对的设备
            }
        }

        /**
         * 已配对设备的事件处理：单击连接设备
         */
        connDevices.setAdapter(listAdapter);
        connDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //new ConnectedThread(address).start();
            }
        });

        discAdapter = new ArrayAdapter<String>(context, R.layout.devices_item, R.id.device_name);
        /**
         * 查找设备列表的事件处理：单击配对并连接
         */
        discoveredDevices.setAdapter(discAdapter);
        discoveredDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String[] split = discAdapter.getItem(position).toString().split("\\\n");
                String name = split[0];
                String address = split[1];
                Log.e("BLE", "onItemClick: 蓝牙名称： "+name+"  MAC地址: "+address);
                BLEConnectThread bleConnectThread = new BLEConnectThread(address,bluetoothAdapter);      //配对并连接设备
                bleConnectThread.start();
                if(bleConnectThread.isConntected){
                    Toast.makeText(getContext(),"连接设备成功！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         *   查找设备
         */
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    myToast.error();
                //startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));   //调用系统的蓝牙界面
                if (bluetoothAdapter != null) {                //支持蓝牙
                    if (!bluetoothAdapter.isEnabled()) {       //当前蓝牙不可用,打开蓝牙
                        Intent enableBLE = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBLE, REQUEST_ENABLE_BT);
                    }
                }
                //弹出正在搜索Toast
                myToast = new LoadToast(viewGroup.getContext());
                myToast.setTranslationY(1020);       //偏移Y轴
                myToast.setText("search...");
                myToast.show();

                //注册广播接收信号
                IntentFilter intent = new IntentFilter();
                intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
                intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED); //每当扫描模式变化的时候，应用程序可以为通过ACTION_SCAN_MODE_CHANGED值来监听全局的消息通知。比如，当设备停止被搜寻以后，该消息可以被系统通知給应用程序。
                intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED); //每当蓝牙模块被打开或者关闭，应用程序可以为通过ACTION_STATE_CHANGED值来监听全局的消息通知。
                bluetoothAdapter.startDiscovery();
                context.registerReceiver(searchReceiver, intent);
                isRegister = true;
            }
        });
    }

    /**
     *  网格holder类
     **/
    class GridHolder {
        ImageView icon;
        TextView text;
    }


}





