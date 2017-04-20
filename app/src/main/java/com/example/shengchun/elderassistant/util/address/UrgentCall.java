package com.example.shengchun.elderassistant.util.address;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shengchun.elderassistant.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sencer
 * @create 2017/4/10
 * @vertion 1.0
 * @description
 */

public class UrgentCall extends Activity {
    private ListView listView;
    private List<Map<String, Object>> dataList;
    private int[] icon_call = {R.drawable.ambulance, R.drawable.police, R.drawable.fire_alarm, R.drawable.traffic,R.drawable.mobile};
    private String[] calls = {"急救电话", "报警电话", "火警电话", "交警电话","China Mobile"};
    private String[] numbers = {"120", "110", "119", "122","10086"};
    private final String CALL_ACTION = "android.intent.action.CALL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.urgent_call);
        init();
    }

    private void init() {
        listView = (ListView) findViewById(R.id.urgent_call_list);
        dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < calls.length; i++) {
            String call = calls[i];
            String number = numbers[i];
            map.put("call", call);
            map.put("number", number);
            map.put("icon", icon_call[i]);
            dataList.add(map);
        }
        listView.setAdapter(new MyAdapter());
        listView.setOnItemClickListener(itemClickListener);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(CALL_ACTION, Uri.parse("tel:" + numbers[position]));
            startActivity(i);
        }
    };
    /**
     * 返回按钮
     * */
    public void back_click(View view) {
        finish();
    }

    class MyAdapter extends BaseAdapter{
        public MyAdapter(){

        }
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if(convertView == null){
                holder = new Holder();
                convertView = LayoutInflater.from(getBaseContext()).inflate(R.layout.calls_item,null);
                holder.call = (TextView) convertView.findViewById(R.id.urgent_type);
                holder.number = (TextView) convertView.findViewById(R.id.call_number);
                holder.icon = (ImageView) convertView.findViewById(R.id.iv_call);
                convertView.setTag(holder);
            }else{
                holder = (Holder) convertView.getTag();
            }
            holder.icon.setImageDrawable(getResources().getDrawable(icon_call[position]));
            holder.call.setText(calls[position]);
            holder.number.setText(numbers[position]);

            return convertView;
        }
    }
    class Holder {
        ImageView icon;
        TextView call;
        TextView number;
    }
}


class Call{
    private String callType;
    private String number;
    public Call(String callType,String number){
        this.callType = callType;
        this.number = number;
    }
    public String getNumber() {
        return number;
    }

    public String getCallType() {
        return callType;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }



}
