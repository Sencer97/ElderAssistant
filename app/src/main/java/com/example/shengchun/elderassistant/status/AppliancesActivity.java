package com.example.shengchun.elderassistant.status;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.shengchun.elderassistant.R;
import com.suke.widget.SwitchButton;

public class AppliancesActivity extends Activity {
    private Toolbar toolbar;
    private SwitchButton sb_tv,sb_air,sb_light;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliances);
        init();
    }
    private void init() {
        toolbar = (Toolbar) findViewById(R.id.appliances_toolbar);
        sb_air = (SwitchButton) findViewById(R.id.sb_air);
        sb_light = (SwitchButton) findViewById(R.id.sb_light);
        sb_tv = (SwitchButton) findViewById(R.id.sb_tv);

        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SwitchButton.OnCheckedChangeListener checkedChangeListener = new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                switch (view.getId()){
                    case R.id.sb_air:
                        if (isChecked){
                            //turn on the air

                            Toast.makeText(getBaseContext(),"Turn On air",Toast.LENGTH_SHORT).show();
                        }else {
                            //turn off the air


                            Toast.makeText(getBaseContext(),"Turn Off air",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.sb_tv:
                        if (isChecked){
                            //turn on the TV

                            Toast.makeText(getBaseContext(),"Turn On TV",Toast.LENGTH_SHORT).show();
                        }else {
                            //turn off the TV

                            Toast.makeText(getBaseContext(),"Turn Off TV",Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case R.id.sb_light:
                        if (isChecked){
                            //turn on the light

                            Toast.makeText(getBaseContext(),"Turn On light",Toast.LENGTH_SHORT).show();
                        }else {
                            //turn off the light

                            Toast.makeText(getBaseContext(),"Turn Off light",Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
            }
        };
        sb_air.setOnCheckedChangeListener(checkedChangeListener);
        sb_light.setOnCheckedChangeListener(checkedChangeListener);
        sb_tv.setOnCheckedChangeListener(checkedChangeListener);

    }
}
