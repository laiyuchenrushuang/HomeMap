package com.example.laiji.homemap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class LaiyuView extends View {


    public LaiyuView(Context context) {
        super(context);
    }

    public LaiyuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LaiyuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LaiyuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("lylog", "onDraw:begin ");
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色
        p.setStrokeWidth(10);
        if (Utils.mListpoint.size() == 11) {
            for(int i =0;i<10;i++){
                canvas.drawLine(Utils.mListpoint.get(i).x, Utils.mListpoint.get(i).y, Utils.mListpoint.get(i+1).x, Utils.mListpoint.get(i+1).y, p);// 画线
            }

        }
//        Utils u = new Utils();
//        u.getlistP1();
//        Log.d("lylog", "size: "+Utils.mListpoint1.size());
//        if (Utils.mListpoint1.size() == 6) {
//            for (int i = 0; i <5; i++) {
//                Log.d("lylog", "onDraw: " + i);
//                canvas.drawLine(Utils.mListpoint1.get(i).x, Utils.mListpoint1.get(i).y, Utils.mListpoint1.get(i + 1).x, Utils.mListpoint1.get(i + 1).y, p);// 画线
//            }
//
//        }

    }

    public void setXY(double longitude, double latitude) {
    }
}
