package com.example.shengchun.elderassistant.util.address;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;
import com.example.shengchun.elderassistant.status.AppliancesActivity;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


public class VoiceActivity extends Activity {
    private Toolbar toolbar;
    private ImageButton speak;
    private ListView lv;
    private ArrayAdapter adapter;
    private int count=0;
    private String res = "";
    private static final String TAG = "VoiceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        //初始化语音配置
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"=593f8f65");
        init();
        Log.e(TAG, "onCreate: ");
    }
    private void init() {
        Toast.makeText(getBaseContext(),"请点击下方语音按钮并开始说话~",Toast.LENGTH_SHORT).show();
        toolbar = (Toolbar) findViewById(R.id.voice_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new ArrayAdapter<String>(getBaseContext(),R.layout.devices_item, R.id.device_name);
        lv = (ListView) findViewById(R.id.lv_text);
        lv.setAdapter(adapter);
        speak = (ImageButton) findViewById(R.id.btn_speak);
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            voice();

            }
        });
    }
    public void speak(String content){
        SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.createSynthesizer(this,null);
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME,"nannan");
        speechSynthesizer.setParameter(SpeechConstant.SPEED,"50");
        speechSynthesizer.setParameter(SpeechConstant.VOLUME,"80");
        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);  //设置云端
        //save audio
       // speechSynthesizer.setParameter(SpeechConstant.TTS_AUDIO_PATH,"./sdcard/iflytek.pcm");
        speechSynthesizer.startSpeaking(content,new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }
            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }
            @Override
            public void onSpeakPaused() {

            }
            @Override
            public void onSpeakResumed() {

            }
            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }
            @Override
            public void onCompleted(SpeechError speechError) {

            }
            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {
            }
        });
    }
    public void voice(){
        RecognizerDialog dialog = new RecognizerDialog(this,null);
        dialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        dialog.setParameter(SpeechConstant.ACCENT,"mandarin");
        dialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                res += parseIatResult(recognizerResult.getResultString());
                count++;
                if(count == 2){
                    count = 0;
                    adapter.add(res);
                    adapter.notifyDataSetChanged();
                    //解析听写结果，执行相关操作
                    if(res.contains("开") && res.contains("空调")){
                        //开空调
                        AppliancesActivity.openAirConditioning();
                        speak("主人，空调已帮您打开啦。");
                    }else if(res.contains("关") && res.contains("空调")){
                        AppliancesActivity.closeAirConditioning();
                        speak("主人，空调已帮您关啦。");
                    }else if((res.contains("开") || res.contains("看")) && res.contains("电视")){
                        AppliancesActivity.openTelevision();
                        speak("主人，电视已帮您打开啦。");
                    }else if(res.contains("关") && res.contains("电视")){
                        AppliancesActivity.closeTelevision();
                        speak("主人，电视已帮您关啦。");
                    }else if(res.contains("开") && res.contains("客厅灯")){
                        AppliancesActivity.openGuestRoomBulb();
                        speak("主人，客厅灯已帮您打开啦。");
                    }else if(res.contains("关") && res.contains("客厅灯")){
                        AppliancesActivity.closeGuestRoomBulb();
                        speak("主人，客厅灯已帮您关啦。");
                    }else if(res.contains("开") && res.contains("门")){
                        AppliancesActivity.openDoor();
                        speak("主人，已帮您开门啦。");
                    }else if(res.contains("关") && res.contains("门")){
                        AppliancesActivity.closeDoor();
                        speak("主人，门关好啦。");
                    }else if(res.contains("开") && res.contains("窗帘")){
                        AppliancesActivity.openCurtain();
                        speak("主人，窗帘拉开啦。");
                    }else if(res.contains("关") && res.contains("窗帘")){
                        AppliancesActivity.closeCurtain();
                        speak("主人，窗帘拉上啦。");
                    }else if(res.contains("开") && res.contains("窗") && !res.contains("窗帘")){
                        AppliancesActivity.openWindow();
                        speak("主人，已帮您开窗啦。");
                    }else if(res.contains("关") && res.contains("窗") && !res.contains("窗帘")){
                        AppliancesActivity.closeWindow();
                        speak("主人，已帮您关窗啦。");
                    }else if(res.contains("开") && res.contains("锁")){
                        AppliancesActivity.openHearter();
                        speak("智能锁已经打开了，请及时使用！");
                    }else if(res.contains("关") && res.contains("锁")){
                        AppliancesActivity.closeHearter();
                        speak("锁关好了。");
                    }
                    res = "";
                }
            }
            @Override
            public void onError(SpeechError speechError) {
            }
        });
        dialog.show();
    }

    private String parseIatResult(String resultString) {
        /** json数据格式
        sn sentence       number    第几句
        ls last sentence  boolean   是否最后一句
        bg begin          number    开始
        ed end            number    结束
        ws words          array     词
        cw chinese word    array     中文分词
        w word            string    单字
        sc score          number    分数
        */
        String ret ="";
        try {
            JSONTokener tokener = new JSONTokener(resultString);
            JSONObject joResult = new JSONObject(tokener);
            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret+=obj.getString("w");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "parseIatResult: "+ret);
        return ret;
    }
}
