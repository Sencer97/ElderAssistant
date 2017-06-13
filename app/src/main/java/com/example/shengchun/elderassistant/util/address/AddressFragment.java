package com.example.shengchun.elderassistant.util.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shengchun.elderassistant.R;

/**
 * @author Sencer
 * @create 2017/3/19
 * @vertion 1.0
 * @description 通讯界面
 */

public class AddressFragment extends Fragment {

    private View groupView;
    ImageView freq_contacts,urgent_call;
    LinearLayout freContact, urgentCall,voice;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.address,container,false);
        init();
        return groupView;
    }

    private void init() {
        freq_contacts = (ImageView) groupView.findViewById(R.id.iv_freq_contacts);
        urgent_call = (ImageView) groupView.findViewById(R.id.iv_urgent);
        freContact = (LinearLayout) groupView.findViewById(R.id.ll_address_left);
        urgentCall = (LinearLayout) groupView.findViewById(R.id.ll_address_right);
        voice = (LinearLayout) groupView.findViewById(R.id.ll_voice);
        freContact.setOnClickListener(listener);
        urgentCall.setOnClickListener(listener);
        voice.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_address_left:
                    //跳转到联系人界面
                    startActivity(new Intent(getContext(),ContactsActivity.class));

                    break;
                case R.id.ll_address_right:
                    //跳转到紧急电话
                    startActivity(new Intent(getContext(),UrgentCall.class));
                    break;
                case R.id.ll_voice:
                    //跳转到语音互动
                    startActivity(new Intent(getContext(),VoiceActivity.class));
                    break;
            }
        }
    };

}
