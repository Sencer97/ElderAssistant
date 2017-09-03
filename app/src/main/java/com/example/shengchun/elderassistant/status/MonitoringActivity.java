package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.shengchun.elderassistant.R;

public class MonitoringActivity extends Activity {
    private Toolbar toolbar;
    private VideoView videoView;
    private static final String TAG = "MonitoringActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        Toast.makeText(getBaseContext(),"连接异常...",Toast.LENGTH_SHORT).show();
     //   init();
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
        videoView = (VideoView) findViewById(R.id.vv_monitoring);
        MediaController mediaController = new MediaController(this);
       // File file = new File(Environment.getExternalStorageDirectory(),"test.mp4");
       // videoView.setVideoPath(file.getPath());   //设置播放的文件路径
       // Uri.parse("/mnt/nand/sdcard/test.mp4")
      //  videoView.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/test.mp4"));
        videoView.setVideoPath(Environment.getExternalStorageDirectory().getPath()+"/test.mp4");   //只能播放手机内的视频
        videoView.start();
        videoView.requestFocus();
        videoView.setMediaController(mediaController);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.resume();     //监听播放结束后重新播放，即循环播放
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(TAG, "onError: ");
                return false;
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(videoView !=null){
            videoView.suspend();
        }
    }
}
