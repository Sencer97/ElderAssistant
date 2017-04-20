package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.shengchun.elderassistant.R;

public class RobotControlActivity extends Activity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //requestWindowFeature(Window.FEATURE_NO_TITLE);   继承AppcompatActivity时无效
        setContentView(R.layout.activity_robot_control);
        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.robot_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
