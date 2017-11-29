package com.example.shengchun.elderassistant.util.address;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
 * @description 紧急拨号
 */

public class UrgentCall extends Activity {
    private ListView listView;
    private List<Map<String, Object>> dataList;
    private int[] icon_call = {R.drawable.ambulance, R.drawable.police, R.drawable.fire_alarm, R.drawable.traffic,R.drawable.mobile};
    private String[] calls = {"急救电话", "报警电话", "火警电话", "交警电话"};
    private String[] numbers = {"120", "110", "119", "122"};
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
          //  View veiw = View.inflate(UrgentCall.this,R.layout.popup_dialog,null);
            final int p = position;
            LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.popup_dialog,null);
            final PopupWindow popWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popWindow.setBackgroundDrawable(new ColorDrawable(0));
            //设置动画
            popWindow.setAnimationStyle(R.style.popwin_anim_style);
            //设置popupwindow的位置
            popWindow.showAtLocation(view,Gravity.BOTTOM, 0, 0);
            //设置背景半透明
            backgroundAlpha(0.6f);
            //点击空白位置，popupwindow消失的事件监听，这时候让背景恢复正常
            popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1.0f);
                }
            });
            TextView title = (TextView) layout.findViewById(R.id.pop_title);
            title.setText("紧急拨号： "+numbers[p]);
            Button confirmBtn = (Button) layout.findViewById(R.id.pop_confirm);
            Button cancelBtn = (Button) layout.findViewById(R.id.pop_cancel);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.pop_confirm:
                            Intent i = new Intent(CALL_ACTION, Uri.parse("tel:" + numbers[p]));
                            startActivity(i);
                            popWindow.dismiss();
                            break;
                        case R.id.pop_cancel:
                            popWindow.dismiss();
                            break;
                    }
                }
            };
            confirmBtn.setOnClickListener(listener);
            cancelBtn.setOnClickListener(listener);

        }

        /**
         * 设置屏幕的背景透明度
         * @param bgAlpha
         */


        public void backgroundAlpha(float bgAlpha) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = bgAlpha; // 0.0-1.0
            getWindow().setAttributes(lp);
        }


//            ActionSheetDialog actionSheetDialog = new ActionSheetDialog(getBaseContext(),new String[]{"确定"},null);
//            actionSheetDialog.title("紧急拨打："+numbers[position]);
//            actionSheetDialog.setCanceledOnTouchOutside(true);
//            actionSheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
//                @Override
//                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    switch (position){
//                        case 0:
//                            Intent i = new Intent(CALL_ACTION, Uri.parse("tel:" + numbers[position]));
//                            startActivity(i);
//                    }
//                }
//            });
//         //   actionSheetDialog.cancelText("#ff0000");
//            actionSheetDialog.show();



      //  }
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


