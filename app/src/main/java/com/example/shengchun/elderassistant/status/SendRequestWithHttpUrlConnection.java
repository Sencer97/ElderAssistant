package com.example.shengchun.elderassistant.status;

import android.util.Log;
        import android.os.Handler;
        import android.os.Message;


        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URL;

/**
 * Created by feng on 2016/6/30.
 * 智能模块访问服务器
 */
public class SendRequestWithHttpUrlConnection {

    private static final String TAG = "SendRequestWithHttpUrlConnection";
    private static final int CONNECT= 1;      //从服务器获取信息成功就标记为1

    private HttpURLConnection httpUrlConnection;
    /**要访问的地址*/
    private String address;
    /**要访问的地址*/
    private URL url;
    /**从网页上返回的数据*/
    private StringBuilder response;
    /**当前操作*/
    private String opreation;

    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {  //如果想网络请求成功!!!
            super.handleMessage(msg);
            Log.e("HttpUrlConnection", "handleMessage内部!!!");
            if(msg.what == CONNECT)
            {
             //   Log.e("HttpUrlConnection" ,"response = " +  response.toString());
            }
          //  Log.e("HttpUrlConnection" ,"response = " +  response.toString());
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            Log.e("HttpUrlConnection","系统消息 = " +  msg.toString() );
        }
    };

    public SendRequestWithHttpUrlConnection(String address , String opreation)
    {
        this.address = address;
        this.opreation = opreation;
    }

    /**
     * 向服务器地址，请求数据
     */
    public void RequestInternetConnection()
    {
        httpUrlConnection = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Toast.makeText(MyApplication.getContext() , "发送网络请求", Toast.LENGTH_LONG).show();
                    url = new URL(address);
                    httpUrlConnection = (HttpURLConnection) url.openConnection();
                    httpUrlConnection.setRequestMethod("POST");
                    httpUrlConnection.setReadTimeout(8000);
                    httpUrlConnection.setConnectTimeout(8000);           //设置超过了规定的时间还没有与网络连接
                    InputStream in = httpUrlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null)
                    {
                        response.append(line);
                    }

                    Message msg = new Message();
                    msg.what = CONNECT;
                    msg.obj = response;
                    handler.sendMessage(msg);                  //如果从网上获取消息成功，则返回到MusicTopF中的handler中!!!
                    Log.e("HttpUrlConnection" , "运行到这里!!!");


                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    Log.e("HttpUrlConnection" , "服务器没响应,请稍后再试");
                    e.printStackTrace();
                }
                finally {
                    httpUrlConnection.disconnect();
                    Log.e("HttpUrlConnection", "运行到finally内部!!!");
                }
            }
        }).start();


    }

}

