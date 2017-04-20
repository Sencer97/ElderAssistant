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
 * @description
 */

public class AddressFragment extends Fragment {

    private View groupView;
    ImageView freq_contacts,urgent_call;
    LinearLayout left,right;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.address,container,false);
        init();
        return groupView;
    }

    private void init() {
        freq_contacts = (ImageView) groupView.findViewById(R.id.iv_freq_contacts);
        urgent_call = (ImageView) groupView.findViewById(R.id.iv_urgent);
        left = (LinearLayout) groupView.findViewById(R.id.ll_address_left);
        right = (LinearLayout) groupView.findViewById(R.id.ll_address_right);
        left.setOnClickListener(listener);
        right.setOnClickListener(listener);
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
            }
        }
    };

}
