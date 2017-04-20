package com.example.shengchun.elderassistant.status;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author Sencer
 * @create 2017/4/13
 * @vertion 1.0
 * @description
 */

public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
