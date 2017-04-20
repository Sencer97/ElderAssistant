package com.example.shengchun.elderassistant.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;

/**
 * @author Sencer
 * @create 2017/3/19
 * @vertion 1.0
 * @description
 */

public class SettingFragment extends Fragment {
    private ViewGroup group;
    private RelativeLayout rl_login,rl_feedback,rl_about,rl_notice;
    private Button exit_btn;
    private View.OnClickListener click;
    private Switch showConnectSwitch;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,  Bundle savedInstanceState) {
        group = (ViewGroup) inflater.inflate(R.layout.setting_fragment,container,false);
        init();
        return group;
    }

    private void init() {
        rl_login = (RelativeLayout) group.findViewById(R.id.rl_login);
        rl_feedback = (RelativeLayout) group.findViewById(R.id.rl_feedback);
        rl_about = (RelativeLayout) group.findViewById(R.id.rl_about);
        rl_notice = (RelativeLayout) group.findViewById(R.id.rl_notice);
        exit_btn = (Button) group.findViewById(R.id.exit_btn);
        showConnectSwitch = (Switch) group.findViewById(R.id.switch_connect);
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
        click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.rl_login:
                        Toast.makeText(getContext(),"Login click",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(),LoginActivity.class));
                        break;
                    case R.id.rl_feedback:
                        startActivity(new Intent(getContext(),FeedbackActivity.class));
                        Toast.makeText(getContext(),"feedback click",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rl_about:
                        startActivity(new Intent(getContext(),AboutAppActivity.class));
                        Toast.makeText(getContext(),"about click",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rl_notice:
                        if (showConnectSwitch.isChecked()){
                            showConnectSwitch.setChecked(false);
                        }else{
                            showConnectSwitch.setChecked(true);
                        }
                        break;
                    case R.id.exit_btn:
                        android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(getContext()).setTitle("提示")
                                .setMessage("确定要退出应用？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getActivity().finish();
                                    }
                                })
                                .setNegativeButton("否",null).show();
                    //    dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
                        break;
                }
            }
        };
        rl_about.setOnClickListener(click);
        rl_login.setOnClickListener(click);
        rl_feedback.setOnClickListener(click);
        rl_notice.setOnClickListener(click);
        exit_btn.setOnClickListener(click);
    }



}
