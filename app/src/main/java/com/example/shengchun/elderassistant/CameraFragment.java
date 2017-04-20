package com.example.shengchun.elderassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Sencer
 * @create 2017/3/23
 * @vertion 1.0
 * @description
 */

public class CameraFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera_fragment,container,false);
    }
}
