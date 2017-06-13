package com.example.shengchun.elderassistant.status;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shengchun.elderassistant.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Sencer
 * @create 2017/6/12
 * @vertion 1.0
 * @description
 */

public class SmokeFragment extends Fragment {
    private ViewGroup viewGroup;
    private LineChart mChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_chart, container, false);
        init();
        return viewGroup;
    }

    /**
     * 1.初始化LineChart
     * 2.添加数据x，y轴数据
     * 3.刷新图表
     */
    private void init() {
        mChart = (LineChart) viewGroup.findViewById(R.id.line_chart);
        // 是否在折线图上添加边框
        mChart.setDrawGridBackground(false);
        mChart.setDrawBorders(false);
        // 设置右下角描述
        Description description = new Description();
        description.setText("时间");
        description.setPosition(getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth()-150,30);
        mChart.setDescription(description);
        //设置透明度
        mChart.setAlpha(0.8f);
        //设置网格底下的那条线的颜色
        mChart.setBorderColor(Color.rgb(213, 216, 214));
        //设置高亮显示
        //  mChart.setHighlightEnabled(true);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mChart.setDragEnabled(false);
        //设置是否可以缩放
        mChart.setScaleEnabled(true);
        //设置是否能扩大扩小
        mChart.setPinchZoom(false);

        // 折线图的点，点击展示的布局和数据
        //  MyMarkView mv = new MyMarkView(this);
        //  mChart.setMarkerView(mv);
        // 加载数据
        LineData data = getLineData();
        mChart.setData(data);
        /**
         * ====================3.x，y动画效果和刷新图表等===========================
         */
        //从X轴进入的动画
        // mChart.animateX(2000);
        // mChart.animateY(3000);   //从Y轴进入的动画
        mChart.animateXY(2500, 2500);    //从XY轴一起进入的动画
        //设置最小的缩放
        mChart.setScaleMinima(0.5f, 1f);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);  //设置图最下面显示的类型
        l.setTextSize(25);
        l.setTextColor(Color.rgb(104, 241, 175));
        l.setFormSize(30f);
        // 刷新图表
      //  mChart.invalidate();
    }

    private LineData getLineData() {
        String time;
        Calendar calendar = Calendar.getInstance();
        time = calendar.get(Calendar.HOUR_OF_DAY)+"";
        int t = Integer.parseInt(time);
        String[] xx = {t-12+"",t-9+"", t-6+"", t-3+"", t+""};
        String[] yy = {"0.01", "0.02", "0.01", "0.01", "0.01"};

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xx.length; i++) {
            if(Integer.parseInt(xx[i])<0){
                xVals.add(Integer.parseInt(xx[i])+24+"");
            }
            else xVals.add(xx[i]);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < yy.length; i++) {
            yVals.add(new Entry(Integer.parseInt(xx[i]), Float.parseFloat(yy[i])));
        }

        LineDataSet set2 = new LineDataSet(yVals, "最近12小时");
        //   set1.setDrawCubic(true);  //设置曲线为圆滑的线
        set2.setCubicIntensity(0.2f);
        set2.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set2.setDrawCircles(true);  //设置有圆点
        set2.setLineWidth(2f);    //设置线的宽度
        set2.setCircleSize(5f);   //设置小圆的大小
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setColor(Color.rgb(104, 241, 175));    //设置曲线的颜色

        LineData data = new LineData(set2);
        return data;
    }
}
