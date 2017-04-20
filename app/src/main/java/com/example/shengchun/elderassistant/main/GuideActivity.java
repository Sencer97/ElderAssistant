package com.example.shengchun.elderassistant.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shengchun.elderassistant.R;

import java.util.ArrayList;
import java.util.List;

        /**
         * @author Sencer
         * @create 2017/3/18
         * @vertion 1.0
         * @description 首次开启导向界面
         */
public class GuideActivity extends Activity {
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    List<ImageView> viewList;
    private int[] pics = {R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    LinearLayout ll_points;
    private ImageView[] points = new ImageView[3];
    Button btn;

    /**
     * 判断是否是第一次开启app
     * @return
     */
    public boolean isFirstRun(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (isFirstRun)
        {
            Log.d("debug", "第一次运行");
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            return true;
        } else {
            Log.d("debug", "不是第一次运行");
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logo_guide);
        // 判断是否首次登录程序
        if (isFirstRun()) {
            setContentView(R.layout.activity_guide);
            init();
            initData();
        } else {

          // new Handler().postDelayed(new Thread(),3000);
            try {
                new Thread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(GuideActivity.this,
                    MainActivity.class);
            startActivity(intent);
            this.finish();    //结束当前活动
        }


    }
    /**
     * 延迟多少秒进入主界面
     * @param second 秒
     */
    private void skipActivity(int second) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(GuideActivity.this,
                        MainActivity.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
        }, 1000*second);
    }

    /**
     * 初始化组件
     */
    public void init() {
        viewList = new ArrayList<ImageView>();
        viewPager = (ViewPager) findViewById(R.id.vp_welcome);
        ll_points = (LinearLayout) findViewById(R.id.ll_points);
        points[0] = (ImageView) findViewById(R.id.iv_point_1);
        points[1] = (ImageView) findViewById(R.id.iv_point_2);
        points[2] = (ImageView) findViewById(R.id.iv_point_3);
        //btn = (Button) findViewById(R.id.btn);
    }
    /**
     * 初始化数据
     */
    public void initData(){
        //添加viewPage
        for (int i=0;i<pics.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(pics[i]);
            viewList.add(imageView);
        }
        for (int i = 0; i < pics.length; i++) {
            points[i].setOnClickListener(pointListener);
        }
        resetPointBgc();
        points[0].setImageResource(R.drawable.point_press);
        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = viewList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                container.removeView(viewList.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    //btn.setVisibility(View.VISIBLE);
                    try {
                        new Thread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(GuideActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();    //结束当前活动
                }
                resetPointBgc();
                points[position].setImageResource(R.drawable.point_press);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(pageChangeListener);
        //设置动画效果1
        /**viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
                    float MIN_SCALE = 0.75f;
                    @Override
                    public void transformPage(View view, float position) {
                        int pageWidth = view.getWidth();
                        if (position < -1) { // [-Infinity,-1)
                            // This page is way off-screen to the left.
                            view.setAlpha(0);
                        } else if (position <= 0) { // [-1,0]
                            // Use the default slide transition when
                            // moving to the left page
                            view.setAlpha(1);
                            view.setTranslationX(0);
                            view.setScaleX(1);
                            view.setScaleY(1);
                        } else if (position <= 1) { // (0,1]
                            // Fade the page out.
                            view.setAlpha(1 - position);
                            // Counteract the default slide transition
                            view.setTranslationX(pageWidth * -position);
                            // Scale the page down (between MIN_SCALE and 1)
                            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                                    * (1 - Math.abs(position));
                            view.setScaleX(scaleFactor);
                            view.setScaleY(scaleFactor);
                        } else { // (1,+Infinity]
                            // This page is way off-screen to the right.
                            view.setAlpha(0);

                        }
                    }
                }
        );*/
        //设置动画效果2
        /*viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
                    float MIN_SCALE = 0.85f;
                    float MIN_ALPHA = 0.5f;

                    @Override
                    public void transformPage(View view, float position) {
                        int pageWidth = view.getWidth();
                        int pageHeight = view.getHeight();

                        if (position < -1) { // [-Infinity,-1)
                            // This page is way off-screen to the left.
                            view.setAlpha(0);
                        } else if (position <= 1) { // [-1,1]
                            // Modify the default slide transition to
                            // shrink the page as well
                            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                            if (position < 0) {
                                view.setTranslationX(horzMargin - vertMargin / 2);
                            } else {
                                view.setTranslationX(-horzMargin + vertMargin / 2);
                            }
                            // Scale the page down (between MIN_SCALE and 1)
                            view.setScaleX(scaleFactor);
                            view.setScaleY(scaleFactor);
                            // Fade the page relative to its size.
                            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                                    / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
                        } else { // (1,+Infinity]
                            // This page is way off-screen to the right.
                            view.setAlpha(0);
                        }
                    }
                }
        );*/

    }

    private void resetPointBgc() {
        for (int i = 0; i < pics.length; i++) {
            points[i].setImageResource(R.drawable.point_normal);
        }
    }


    View.OnClickListener pointListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.iv_point_1:
                    viewPager.setCurrentItem(0);
                    points[0].setImageResource(R.drawable.point_press);
                    break;
                case R.id.iv_point_2:
                    viewPager.setCurrentItem(1);
                    points[1].setImageResource(R.drawable.point_press);
                    break;
                case R.id.iv_point_3:
                    viewPager.setCurrentItem(2);
                    points[2].setImageResource(R.drawable.point_press);
                    break;
                default:break;

            }
        }
    };

}
