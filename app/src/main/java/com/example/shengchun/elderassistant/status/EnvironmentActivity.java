package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.shengchun.elderassistant.R;

public class EnvironmentActivity extends Activity {
    private Toolbar toolbar;                //顶部工具栏
    private TextView temperature;           //温度
    private TextView humidity;              //湿度
    private TextView pm2_5;                 //PM2.5
    private TextView CO;                    //CO浓度
    private TextView smoke;                 //烟雾浓度
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefreshing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);
        init();
    }
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.environment_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 传感器接收的数据
         */
        temperature = (TextView) findViewById(R.id.temperature);
        humidity = (TextView) findViewById(R.id.humidity);
        pm2_5 = (TextView) findViewById(R.id.pm2_5);
        CO = (TextView) findViewById(R.id.co);
        smoke = (TextView) findViewById(R.id.smoke);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.wrl_refresh);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.RED);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isRefreshing){
                    isRefreshing = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            isRefreshing = false;
                            //执行刷新
                            temperature.setText("25 ℃");
                            humidity.setText("50 %");
                            pm2_5.setText("63 μg/m³");
                            CO.setText("0.02 %");
                            smoke.setText("0.04 %");
                           // Toast.makeText(getBaseContext(),"刷新成功！",Toast.LENGTH_SHORT).show();
                            Snackbar.make(temperature,"刷新成功~",Snackbar.LENGTH_SHORT).show();
                        }
                    }, 2000);   //转圈圈两秒
                    //刷新完更新数据   在handler中完成

                }
            }

        });

    }
}
