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
 * ����ģ����ʷ�����
 */
public class SendRequestWithHttpUrlConnection {

    private static final String TAG = "SendRequestWithHttpUrlConnection";
    private static final int CONNECT= 1;      //�ӷ�������ȡ��Ϣ�ɹ��ͱ��Ϊ1

    private HttpURLConnection httpUrlConnection;
    /**Ҫ���ʵĵ�ַ*/
    private String address;
    /**Ҫ���ʵĵ�ַ*/
    private URL url;
    /**����ҳ�Ϸ��ص�����*/
    private StringBuilder response;
    /**��ǰ����*/
    private String opreation;

    private  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {  //�������������ɹ�!!!
            super.handleMessage(msg);
            Log.e("HttpUrlConnection", "handleMessage�ڲ�!!!");
            if(msg.what == CONNECT)
            {
             //   Log.e("HttpUrlConnection" ,"response = " +  response.toString());
            }
          //  Log.e("HttpUrlConnection" ,"response = " +  response.toString());
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            Log.e("HttpUrlConnection","ϵͳ��Ϣ = " +  msg.toString() );
        }
    };

    public SendRequestWithHttpUrlConnection(String address , String opreation)
    {
        this.address = address;
        this.opreation = opreation;
    }

    /**
     * ���������ַ����������
     */
    public void RequestInternetConnection()
    {
        httpUrlConnection = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Toast.makeText(MyApplication.getContext() , "������������", Toast.LENGTH_LONG).show();
                    url = new URL(address);
                    httpUrlConnection = (HttpURLConnection) url.openConnection();
                    httpUrlConnection.setRequestMethod("POST");
                    httpUrlConnection.setReadTimeout(8000);
                    httpUrlConnection.setConnectTimeout(8000);           //���ó����˹涨��ʱ�仹û������������
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
                    handler.sendMessage(msg);                  //��������ϻ�ȡ��Ϣ�ɹ����򷵻ص�MusicTopF�е�handler��!!!
                    Log.e("HttpUrlConnection" , "���е�����!!!");


                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e) {
                    Log.e("HttpUrlConnection" , "������û��Ӧ,���Ժ�����");
                    e.printStackTrace();
                }
                finally {
                    httpUrlConnection.disconnect();
                    Log.e("HttpUrlConnection", "���е�finally�ڲ�!!!");
                }
            }
        }).start();


    }

}

