package com.example.shengchun.elderassistant.util.address;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.shengchun.elderassistant.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sencer
 * @create 2017/3/19
 * @vertion 1.0
 * @description 通讯界面
 */

public class AddressFragment extends Fragment {

    private View groupView;
//    private ImageView freq_contacts,urgent_call;
    private LinearLayout freContact, urgentCall,voice;
    private TextSwitcher textSwitcher;
    private String[] items = {"出门记得随手关电！","雷雨天气时及时断电哦！","注意保持卫生清洁！"};
    private int index = 0;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private List<ImageView> viewList;
    private int[] pics = {R.drawable.remind_1,R.drawable.remind_2,R.drawable.remind_3};
    private LinearLayout ll_points;
    private ImageView[] points = new ImageView[3];
    private int p ;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        groupView = inflater.inflate(R.layout.address,container,false);
        init();
        return groupView;
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
                    viewPager.setCurrentItem(1);
                    points[0].setImageResource(R.drawable.point_press);
                    break;
                case R.id.iv_point_2:
                    viewPager.setCurrentItem(2);
                    points[1].setImageResource(R.drawable.point_press);
                    break;
                case R.id.iv_point_3:
                    viewPager.setCurrentItem(3);
                    points[2].setImageResource(R.drawable.point_press);
                    break;
                default:break;
            }
        }
    };
    private void initVP(){
        WindowManager wm = (WindowManager) getContext().getSystemService(getContext().WINDOW_SERVICE);
        //图片轮播
        viewList = new ArrayList<ImageView>();
        viewPager = (ViewPager) groupView.findViewById(R.id.vp_welcome);
        ll_points = (LinearLayout) groupView.findViewById(R.id.ll_points);
        points[0] = (ImageView) groupView.findViewById(R.id.iv_point_1);
        points[1] = (ImageView) groupView.findViewById(R.id.iv_point_2);
        points[2] = (ImageView) groupView.findViewById(R.id.iv_point_3);
        //设置一屏显示多个页面 同时xml文件设置clipChildren属性为false
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.width = wm.getDefaultDisplay().getWidth() - 60;
        viewPager.setLayoutParams(layoutParams);

//        viewPager.setPageMargin(100);
        viewPager.setOffscreenPageLimit(3);
        ImageView image = new ImageView(getContext());
        image.setImageResource(pics[2]);
        viewList.add(image);
        //添加viewPage
        for (int i=0;i<pics.length;i++){
            ImageView img = new ImageView(getContext());
            img.setImageResource(pics[i]);
            viewList.add(img);
        }
        ImageView iv = new ImageView(getContext());
        iv.setImageResource(pics[0]);
        viewList.add(iv);
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
            /**
             * 页面宽度所占ViewPager测量宽度的权重比例，默认为1
             --------适用于显示两个页面
            @Override
            public float getPageWidth(int position) {
                return (float) 0.8;
            }*/
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
            boolean isFirst = true;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (!isFirst)
                    handle.removeMessages(1);
                isFirst = false;
            }
            @Override
            public void onPageSelected(int position) {
                if(position == 4){
                    p = position = 1;
                }else if(position == 0){
                    p = position = 3;
                }else {
                    p = position;
                }
                resetPointBgc();
                points[p-1].setImageResource(R.drawable.point_press);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_IDLE == state){
                    viewPager.setCurrentItem(p, false);
                    handle.sendEmptyMessageDelayed(1, 2000);
                }
            }
        };
        viewPager.setCurrentItem(1);
        handle.sendEmptyMessageDelayed(1, 2000);
        viewPager.setAdapter(pagerAdapter);
        //设置轮播动画

        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
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
        );
        viewPager.setOnPageChangeListener(pageChangeListener);
    }
    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            p++;
            viewPager.animate();
            viewPager.setCurrentItem(p, false);
            handle.sendEmptyMessageDelayed(1, 2000);
        }
    };
    private void init() {
        initVP();
        //实用工具
        freContact = (LinearLayout) groupView.findViewById(R.id.ll_address_left);
        urgentCall = (LinearLayout) groupView.findViewById(R.id.ll_address_right);
        voice = (LinearLayout) groupView.findViewById(R.id.ll_voice);
        //文字轮播
        textSwitcher = (TextSwitcher) groupView.findViewById(R.id.ts_remind);
        freContact.setOnClickListener(listener);
        urgentCall.setOnClickListener(listener);
        voice.setOnClickListener(listener);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(getContext());
                tv.setTextSize(20);
                tv.setTextColor(getResources().getColor(R.color.text));
                return tv;
            }
        });
        textSwitcher.setInAnimation(getContext(),R.anim.slide_in_bottom);
        textSwitcher.setOutAnimation(getContext(),R.anim.slide_out_top);
        Message msg = handler.obtainMessage(1);
        msg.what = index;
        handler.sendMessage(msg);
    }
        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textSwitcher.setText(items[index % items.length]);
                index++;
                Message mesg = handler.obtainMessage(1);
                mesg.what = index;
                handler.sendMessageDelayed(mesg, 3000);

            }
        };
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
