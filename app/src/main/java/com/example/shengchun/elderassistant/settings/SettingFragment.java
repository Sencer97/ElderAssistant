package com.example.shengchun.elderassistant.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;

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
    private Switch showConnectSwitch;      //转换器
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
        group = (ViewGroup) inflater.inflate(R.layout.setting_fragment,container,false);
        init();
        return group;
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
        showConnectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //on
                    Toast.makeText(getContext(),"On",Toast.LENGTH_SHORT).show();
                }else {
                    //off
                    Toast.makeText(getContext(),"Off",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
