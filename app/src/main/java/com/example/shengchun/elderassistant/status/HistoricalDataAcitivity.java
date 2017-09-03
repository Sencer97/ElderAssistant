package com.example.shengchun.elderassistant.status;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.shengchun.elderassistant.R;

import java.util.ArrayList;
import java.util.List;

public class HistoricalDataAcitivity extends FragmentActivity {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    //5个碎片界面
    private List<Fragment> fragments;
    private TempFragment tempFragment;
    private HumFragment humFragment;
    private PMFragment pmFragment;
    private CoFragment coFragment;
    private SmokeFragment smokeFragment;

    private int offsetWidth = 0;       //偏移量
    private int screenWith = 0;        //屏幕宽度
    View line;
    private TextView tmp,hum,pm,co,smoke;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //取消标题栏，当继承AppCompatActivity时无效
        setContentView(R.layout.activity_history_data_acitivity);
        toolbar = (Toolbar) findViewById(R.id.history_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    private void init() {
        line = findViewById(R.id.underline);
        //获取屏幕宽度
        screenWith = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        final ViewGroup.LayoutParams lp = line.getLayoutParams();
        offsetWidth = lp.width = screenWith / 4;
        line.setLayoutParams(lp);
        tmp = (TextView) findViewById(R.id.his_temp);
        hum = (TextView) findViewById(R.id.his_hum);
        pm = (TextView) findViewById(R.id.his_pm);
        co = (TextView) findViewById(R.id.his_co);
        smoke = (TextView) findViewById(R.id.his_smoke);
        tmp.setOnClickListener(clickListener);
        hum.setOnClickListener(clickListener);
        co.setOnClickListener(clickListener);
        smoke.setOnClickListener(clickListener);
        viewPager = (ViewPager) findViewById(R.id.vp_data);
        fragments = new ArrayList<Fragment>();
        tempFragment = new TempFragment();
        humFragment = new HumFragment();
        pmFragment = new PMFragment();
        coFragment = new CoFragment();
        smokeFragment = new SmokeFragment();
        fragments.add(tempFragment);
        fragments.add(humFragment);
      //  fragments.add(pmFragment);
        fragments.add(coFragment);
        fragments.add(smokeFragment);
        //初始化指示器
        tmp.setTextColor(getResources().getColor(R.color.text_pressed));
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         //  private boolean isAnim = false;
         //   private int pos = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                resetTextViewColor();
                switch (position){
                    case 0:
                        tmp.setTextColor(getResources().getColor(R.color.text_pressed));
                        break;
                    case 1:
                        hum.setTextColor(getResources().getColor(R.color.text_pressed));
                        break;
//                    case 2:
//                        pm.setTextColor(getResources().getColor(R.color.text_pressed));
//                        break;
                    case 2:
                        co.setTextColor(getResources().getColor(R.color.text_pressed));
                        break;
                    case 3:
                        smoke.setTextColor(getResources().getColor(R.color.text_pressed));
                        break;
                }
                line.setTranslationX(offsetWidth*position);
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
               case R.id.his_temp:
                   viewPager.setCurrentItem(0);
                   tmp.setTextColor(getResources().getColor(R.color.text_pressed));
                   line.setTranslationX(offsetWidth*0);
                   break;
               case R.id.his_hum:
                   viewPager.setCurrentItem(1);
                   hum.setTextColor(getResources().getColor(R.color.text_pressed));
                   line.setTranslationX(offsetWidth*1);
                   break;
               case R.id.his_co:
                   viewPager.setCurrentItem(2);
                   co.setTextColor(getResources().getColor(R.color.text_pressed));
                   line.setTranslationX(offsetWidth*2);
                   break;
               case R.id.his_smoke:
                   viewPager.setCurrentItem(3);
                   smoke.setTextColor(getResources().getColor(R.color.text_pressed));
                   line.setTranslationX(offsetWidth*3);
                   break;
            }
        }
    };
    private void resetTextViewColor() {
        tmp.setTextColor(getResources().getColor(R.color.text_normal));
        hum.setTextColor(getResources().getColor(R.color.text_normal));
        pm.setTextColor(getResources().getColor(R.color.text_normal));
        co.setTextColor(getResources().getColor(R.color.text_normal));
        smoke.setTextColor(getResources().getColor(R.color.text_normal));

    }

}
