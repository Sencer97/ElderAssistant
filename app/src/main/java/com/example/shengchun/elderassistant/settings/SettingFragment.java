package com.example.shengchun.elderassistant.settings;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.example.shengchun.elderassistant.R;
import com.example.shengchun.elderassistant.status.EnvironmentActivity;

/**
 * @author Sencer
 * @create 2017/3/19
 * @vertion 1.0
 * @description   设置界面
 */

public class SettingFragment extends Fragment {
    private ViewGroup group;
    private RelativeLayout rl_login;
    private RelativeLayout rl_feedback;
    private RelativeLayout rl_about;
    private RelativeLayout rl_notice;
    private RelativeLayout rl_exit;
    private View.OnClickListener click;     //布局点击监听
    private CompoundButton.OnCheckedChangeListener checkedChangeListener;    //通知显示
    private Switch showConnectSwitch;      //通知栏监听

    //通知栏显示的监测数据
    private String tmp = null;
    private String humdity = null;
    private String pm2_5 = null;
    private String co = null;
    private String smoke = null;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
        group = (ViewGroup) inflater.inflate(R.layout.setting_fragment,container,false);
        init();
        return group;
    }
    public PendingIntent getDefalutIntent(int flags){
        Intent notifyIntent = new Intent(getContext(), EnvironmentActivity.class);
        /*notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);     //防止点击后重复建立activity*/
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), (int)SystemClock.uptimeMillis(), notifyIntent, flags);
        return pendingIntent;
    }
    /**
     * 初始化布局
     */
    private void init() {
        rl_login = (RelativeLayout) group.findViewById(R.id.rl_login);
        rl_feedback = (RelativeLayout) group.findViewById(R.id.rl_feedback);
        rl_about = (RelativeLayout) group.findViewById(R.id.rl_about);
        rl_notice = (RelativeLayout) group.findViewById(R.id.rl_notice);
        rl_exit = (RelativeLayout) group.findViewById(R.id.rl_exit_app);
        showConnectSwitch = (Switch) group.findViewById(R.id.switch_connect);
        //switch 切换监听
        checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tmp = "25 ℃";
                humdity = "50 %";
                pm2_5 = "63 μg/m³";
                co = "0.02 %";
                smoke = "0.04 %";
                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext());
                builder.setStyle(new NotificationCompat.InboxStyle()
                        .addLine("温度：" + tmp)
                        .addLine("湿度：" + humdity)
                        .addLine("PM2.5：" + pm2_5)
                        .addLine("CO毒气：" +co )
                        .addLine("烟雾浓度：" +smoke)
                        .setBigContentTitle("监测数据")
                )
                        .setAutoCancel(false)       //true：点击后自动取消
                        .setOngoing(true)
                        .setContentIntent(getDefalutIntent(PendingIntent.FLAG_UPDATE_CURRENT))
                        .setTicker("监测数据来啦~~~")  //首次出现带有上升效果
//                        .setWhen(System.currentTimeMillis())         //通知产生的时间
                        .setPriority(Notification.PRIORITY_MIN)   //通知的优先级 后台信息
                        .setSmallIcon(R.drawable.environment)
                ;
                if(isChecked){
                    //开启数据通知常驻
                    notificationManager.notify(1,builder.build());
                }else {
                    //取消通知常驻
                    notificationManager.cancel(1);
                }
            }
        };
        showConnectSwitch.setOnCheckedChangeListener(checkedChangeListener);

        //布局点击事件监听
        click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.rl_login:
                        startActivity(new Intent(getContext(),LoginActivity.class));
                        break;
                    case R.id.rl_feedback:
                        startActivity(new Intent(getContext(),FeedbackActivity.class));
                        break;
                    case R.id.rl_about:
                        startActivity(new Intent(getContext(),AboutAppActivity.class));
                        break;
                    case R.id.rl_notice:
                        if (showConnectSwitch.isChecked()){
                            showConnectSwitch.setChecked(false);
                        }else{
                            showConnectSwitch.setChecked(true);
                        }
                        break;
                    case R.id.rl_exit_app:
                        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(getContext()).setTitle("提示")
                                .setMessage("确定要退出应用？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().finish();
                                    }
                                })
                                .setNegativeButton("否",null).show();
                        break;
                }
            }
        };
        rl_about.setOnClickListener(click);
        rl_login.setOnClickListener(click);
        rl_feedback.setOnClickListener(click);
        rl_notice.setOnClickListener(click);
        rl_exit.setOnClickListener(click);
    }



}
