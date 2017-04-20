package com.example.shengchun.elderassistant.status;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shengchun.elderassistant.R;

import net.steamcrafted.loadtoast.LoadToast;

/**
 * @author Sencer
 * @create 2017/3/19
 * @vertion 1.0
 * @description
 */

public class RobotFragment extends Fragment {
    private static final int REQUEST_ENABLE_BT = 1;
    private ViewGroup viewGroup;
    private int[] gridIcons = {R.drawable.robot_control,R.drawable.environment,R.drawable.light,R.drawable.monitoring};
    private String[] gridText = {"遥控机器","环境监控","家电控制","远程监视"};
    private GridView gridView;
    private RelativeLayout relativeLayout;
    private LoadToast myToast;
    private BluetoothAdapter bluetoothAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.robot_fragment,container,false);
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myToast.success();
              //  myToast.error();
            }
        });
        init();
        int[] a;

        return viewGroup;
    }

    /**
     *  gridView的适配器
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
            if(convertView == null){
                holder = new GridHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item,null);
                holder.icon = (ImageView) convertView.findViewById(R.id.iv_item);
                holder.text = (TextView) convertView.findViewById(R.id.tv_item);       //注意是要convertView 来findView！！！
                convertView.setTag(holder);
            }else{
                holder = (GridHolder) convertView.getTag();
            }
            holder.icon.setImageResource(gridIcons[position]);
            holder.text.setText(gridText[position]);
            return convertView;
        }
    };

    /**
     *  gridView 点击事件
     */
    AdapterView.OnItemClickListener gridItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:         //进入遥控机器界面
                    startActivity(new Intent(getContext(),RobotControlActivity.class));
                    break;
                case 1:         //进入环境监控界面
                    startActivity(new Intent(getContext(),EnvironmentActivity.class));
                    break;
                case 2:         //进入家电控制界面
                    startActivity(new Intent(getContext(),AppliancesActivity.class));
                    break;
                case 3:         //进入远程监控界面
                    startActivity(new Intent(getContext(),MonitoringActivity.class));
                    break;
            }
        }
    };
    private void init() {
        gridView = (GridView) viewGroup.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(gridItemClick);

        relativeLayout = (RelativeLayout) viewGroup.findViewById(R.id.query);
        /**
         *   查找设备
         */
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myToast = new LoadToast(viewGroup.getContext());
                myToast.setTranslationY(1120);       //偏移Y轴
                myToast.setText("search...");
                myToast.show();
          //    myToast.error();
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if(bluetoothAdapter != null){         //支持蓝牙
                    if(!bluetoothAdapter.isEnabled()){       //当前蓝牙不可用
                        Intent enableBLE = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBLE,REQUEST_ENABLE_BT);
                    }
                }

            }
        });

    }
    class GridHolder{
        ImageView icon;
        TextView text;
    }

}





