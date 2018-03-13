package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.VideoView;

import com.example.shengchun.elderassistant.R;

public class MonitoringActivity extends Activity {
    private Toolbar toolbar;
    private VideoView videoView;
    private WebView webView;
    private static final String TAG = "MonitoringActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
      //  Toast.makeText(getBaseContext(),"连接异常...",Toast.LENGTH_SHORT).show();
        init();
    }
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.monitoring_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;// 返回false
            }
        });


        webView.loadUrl("http://192.168.1.1:8080/?action=stream");
//        webView.loadUrl("http://18332659ak.iok.la:19343/?action=stream");

        WebSettings webSettings = webView.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);


        //  videoView = (VideoView) findViewById(R.id.vv_monitoring);
      //  MediaController mediaController = new MediaController(this);
       // File file = new File(Environment.getExternalStorageDirectory(),"test.mp4");
       // videoView.setVideoPath(file.getPath());   //设置播放的文件路径
       // Uri.parse("/mnt/nand/sdcard/test.mp4")
      //  videoView.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/test.mp4"));
      //  videoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/test.mp4");   //只能播放手机内的视频
      //  videoView.setVideoURI(Uri.parse("http://192.168.1.1:8080/?action=sstream"));
      //  videoView.start();
      //  videoView.requestFocus();
      //  videoView.setMediaController(mediaController);

//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                videoView.resume();     //监听播放结束后重新播放，即循环播放
//            }
//        });
//        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//            @Override
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                Log.e(TAG, "onError: ");
//                return false;
//            }
//        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
//        if(videoView !=null){
//            videoView.suspend();
//        }
    }
}
