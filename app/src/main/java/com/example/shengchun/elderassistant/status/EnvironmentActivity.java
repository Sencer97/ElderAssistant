package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.shengchun.elderassistant.R;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class EnvironmentActivity extends Activity {
    private Toolbar toolbar;                //顶部工具栏
    private TextView temperature;           //温度
    private TextView humidity;              //湿度
    private TextView pm2_5;                 //PM2.5
    private TextView CO;                    //CO浓度
    private TextView smoke;                 //烟雾浓度
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefreshing = false;
    private String[] tmp = {"27","28","27","28"},
                    hum = {"35","33","33","34"},
                    pm = {"65","68","64","65"},
                    co = {"0.02","0.03","0.02","0.01"},
                    smk = {"0.01","0.02","0.01","0.02"};
    private static int cnt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);
        init();
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(EnvironmentActivity.this,SweetAlertDialog.WARNING_TYPE)
                .setTitleText("温馨提示")
                .setContentText("室外天气炎热，出门请注意防晒哦！")
                .setConfirmText("知道啦~")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.setConfirmClickListener(null)
                                .setCancelClickListener(null);
                        sweetAlertDialog.dismiss();
                    }
                });
        sweetAlertDialog.show();
//                        new Thread(){
//                            @Override
//                            public void run() {
//                                try {
//                                    URL url = new URL("http://bxu2711760781.my3w.com/json/json_array.php");
//                                    HttpURLConnection conn= (HttpURLConnection)url.openConnection();
//                                    conn.setConnectTimeout(10000);
//                                    conn.setRequestMethod("GET");
//                                    conn.setRequestProperty("accept", "*/*");
//                                    conn.connect();
//                                    InputStream is = conn.getInputStream();
//                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                                    StringBuilder sb = new StringBuilder();
//                                    String s = "";
//                                    while((s = reader.readLine())!=null){
//                                        sb.append(s);
//                                    }
//                                    Log.i("JSON", "json----->"+sb.toString());
//                                    try {
//                                        JSONObject jo = new JSONObject(sb.toString());
//                                        JSONArray jsonArray = (JSONArray) jo.get("data");
//                                        for (int i = 0; i < jsonArray.length(); ++i) {
//                                            JSONObject o = (JSONObject) jsonArray.get(i);
//                                            Log.i("JSON","data--->  温度： "+o.getString("tmp")+"  湿度：  "+o.getString("humidity"));
//                                        }
//                                    } catch (JSONException e) {
//                                        Log.i("JSON", "json error--->"+e.toString());
//                                    }
//
//                                } catch (MalformedURLException e) {
//                                    e.printStackTrace();
//                                    Log.i("JSON", e.toString());
//                                } catch (IOException e) {
//                                    Log.i("JSON", e.toString());
//                                }
//
//                            }
//                        }.start();
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
        toolbar.inflateMenu(R.menu.menu_history);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.history){
                    startActivity(new Intent(getBaseContext(),HistoricalDataAcitivity.class));
                }
                return true;
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
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN,Color.RED);
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
                           for(int i=0;i<tmp.length;i++){
                               if(cnt == i){
                                   temperature.setText(tmp[i]+" ℃");
                                   humidity.setText(hum[i]+" %");
                                   pm2_5.setText(pm[i]+" μg/m³");
                                   CO.setText(co[i]+" %");
                                   smoke.setText(smk[i]+" %");
                                   cnt++;
                                   if(cnt == tmp.length-1){
                                       cnt = 0;
                                   }
                                   //cnt = (cnt<tmp.length)?(cnt+1):0;
                                   break;
                               }
                           }

                            Snackbar.make(temperature,"刷新成功~",Snackbar.LENGTH_SHORT).show();
                        }
                    }, 2500);   //转圈圈3秒
                    //刷新完更新数据   在handler中完成
                }
            }

        });

    }

}
