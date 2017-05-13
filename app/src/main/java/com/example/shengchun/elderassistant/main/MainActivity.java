package com.example.shengchun.elderassistant.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shengchun.elderassistant.R;
import com.example.shengchun.elderassistant.settings.SettingFragment;
import com.example.shengchun.elderassistant.status.RobotFragment;
import com.example.shengchun.elderassistant.util.address.AddressFragment;

import java.util.ArrayList;
import java.util.List;

/**
     * @author Sencer
     * @create 2017/3/18
     * @vertion 1.0
     * @description  应用主界面
     */

public class MainActivity extends FragmentActivity {
        private static final String TAG = "MainActivity";
        private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    //三个碎片界面
    private List<Fragment> fragments;
    private AddressFragment addressFragment;
    private RobotFragment robotFragment;
    private SettingFragment settingFragment;

    //底部导航
    private LinearLayout ll_robot,ll_address,ll_setting;
    private ImageView iv_robot,iv_address,iv_setting;
    private TextView tv_robot,tv_address,tv_setting;

    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //取消标题栏，当继承AppCompatActivity时无效
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        fragments = new ArrayList<Fragment>();
        robotFragment = new RobotFragment();
        addressFragment = new AddressFragment();
        settingFragment = new SettingFragment();

        fragments.add(robotFragment);
        fragments.add(addressFragment);
        fragments.add(settingFragment);

        //底部导航栏
        ll_robot = (LinearLayout) findViewById(R.id.ll_robot);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_setting = (LinearLayout) findViewById(R.id.ll_setting);

        ll_setting.setOnClickListener(clickListener);
        ll_address.setOnClickListener(clickListener);
        ll_robot.setOnClickListener(clickListener);

        iv_address = (ImageView) findViewById(R.id.iv_address);
        iv_robot = (ImageView) findViewById(R.id.iv_robot);
        iv_setting = (ImageView) findViewById(R.id.iv_setting);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_robot = (TextView) findViewById(R.id.tv_robot);
        tv_setting = (TextView) findViewById(R.id.tv_setting);

        //标题
        title = (TextView) findViewById(R.id.tv_titleBar);

        //初始化图片颜色
        iv_robot.setImageResource(R.drawable.robot_pressed);
        tv_robot.setTextColor(getResources().getColor(R.color.text_pressed));

        //viewPager设置adapter和监听器
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
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                resetImageViewSrc();
                resetTextViewColor();
                switch (position){
                    case 0:
                        iv_robot.setImageResource(R.drawable.robot_pressed);
                        tv_robot.setTextColor(getResources().getColor(R.color.text_pressed));
                        title.setText("状态");
                        break;
                    case 1:
                        iv_address.setImageResource(R.drawable.address_pressed);
                        tv_address.setTextColor(getResources().getColor(R.color.text_pressed));
                        title.setText("通讯");
                        break;
                    case 2:
                        iv_setting.setImageResource(R.drawable.setting_pressed);
                        tv_setting.setTextColor(getResources().getColor(R.color.text_pressed));
                        title.setText("设置");
                        break;
                }
                viewPager.setCurrentItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    private void resetImageViewSrc(){
        iv_robot.setImageResource(R.drawable.robot_nomal);
        iv_address.setImageResource(R.drawable.address_normal);
        iv_setting.setImageResource(R.drawable.setting_normal);
    }
    private void resetTextViewColor(){
        tv_robot.setTextColor(getResources().getColor(R.color.text_normal));
        tv_address.setTextColor(getResources().getColor(R.color.text_normal));
        tv_setting.setTextColor(getResources().getColor(R.color.text_normal));
    }
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_robot:
                    viewPager.setCurrentItem(0);
                    iv_robot.setImageResource(R.drawable.robot_pressed);
                    tv_robot.setTextColor(getResources().getColor(R.color.text_pressed));
                    break;
                case R.id.ll_address:
                    viewPager.setCurrentItem(1);
                    iv_address.setImageResource(R.drawable.address_pressed);
                    tv_address.setTextColor(getResources().getColor(R.color.text_pressed));
                    break;
                case R.id.ll_setting:
                    iv_setting.setImageResource(R.drawable.setting_pressed);
                    tv_setting.setTextColor(getResources().getColor(R.color.text_pressed));
                    viewPager.setCurrentItem(2);
                    break;
            }
        }
    };
        @Override
        public void onBackPressed() {
            super.onBackPressed();
        }
        /**
         * 设置屏幕的背景透明度 1表示不透明，0表示全透明
         * @param bgAlpha
         */
        public void backgroundAlpha(float bgAlpha) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = bgAlpha; // 0.0-1.0
            getWindow().setAttributes(lp);
        }
}
