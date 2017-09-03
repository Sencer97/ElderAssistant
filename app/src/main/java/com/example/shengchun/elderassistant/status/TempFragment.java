package com.example.shengchun.elderassistant.status;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shengchun.elderassistant.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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

public class TempFragment extends Fragment {
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
        mChart.setDescription("时间");
        mChart.setDescriptionTextSize(15f);

        //设置透明度
        mChart.setAlpha(0.8f);
        //设置网格底下的那条线的颜色
        mChart.setBorderColor(Color.rgb(213, 216, 214));
        //设置高亮显示
      //  mChart.setHighlightEnabled(true);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mChart.setDragEnabled(true);
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
        //设置动画效果
        mChart.animateY(2000, Easing.EasingOption.Linear);
        mChart.animateX(2000, Easing.EasingOption.Linear);
        //设置最小的缩放
        mChart.setScaleMinima(0.5f, 1f);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);  //设置图最下面显示的类型
        l.setTextSize(50f);
        l.setTextColor(Color.rgb(104, 241, 175));
        l.setFormSize(50f);

        //设置x轴的样式
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineColor(Color.parseColor("#66CDAA"));
        xAxis.setAxisLineWidth(5);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(12f);
        //设置是否显示x轴
        xAxis.setEnabled(true);

        //设置左边y轴的样式
        YAxis yAxisLeft = mChart.getAxisLeft();
        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisLeft.setAxisLineWidth(5);
        yAxisLeft.setDrawGridLines(false);
        //设置右边y轴的样式
        YAxis yAxisRight = mChart.getAxisRight();
        yAxisRight.setEnabled(false);
        // 刷新图表
        mChart.invalidate();
    }

    private LineData getLineData() {
        String time;
        String[] xx={"","","","",""};
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        String m = calendar.get(Calendar.MINUTE)+"";
        int t=calendar.get(Calendar.MINUTE);
        for (int i = 4,j=0; i >= 0; i--) {
            if(Integer.parseInt(m)<10){
                m="0"+t;
            }
            if(h-i*3<0){
                xx[j++]= (h-i*3+24) +":"+m;
            }else
                xx[j++] = h-3*i+":"+m;
        }
        String[] yy = {"25", "28", "27", "28", "27"};

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xx.length; i++) {
            xVals.add(xx[i]);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < yy.length; i++) {
            yVals.add(new Entry(Integer.parseInt(yy[i]),i));
        }

        LineDataSet set2 = new LineDataSet(yVals, "最近12小时室温变化");
      //  set2.setDrawCubic(true);  //设置曲线为圆滑的线
        set2.setCubicIntensity(0.2f);
        set2.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set2.setDrawCircles(true);  //设置有圆点
        set2.setLineWidth(3f);    //设置线的宽度
        set2.setCircleSize(5f);   //设置小圆的大小
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setColor(Color.rgb(104, 241, 175));    //设置曲线的颜色
        set2.setValueTextSize(13f);
        LineData data = new LineData(xVals,set2);
        return data;
    }
}
