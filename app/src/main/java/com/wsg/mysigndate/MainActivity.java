package com.wsg.mysigndate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wsg.mysign.mydatepicker.DPCManager;
import com.wsg.mysign.mydatepicker.DPDecor;
import com.wsg.mysign.mydatepicker.DPMode;
import com.wsg.mysign.mydatepicker.DatePicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DatePicker.OnClickSignIn {

    @Bind(R.id.my_datepicker)
    DatePicker myDatepicker;
    @Bind(R.id.btn_original_demo)
    Button btnOriginalDemo;

    private Context mContext;

    private DPCManager dpcManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        init();

        btnOriginalDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,YuanbanDemoActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        dpcManager = DPCManager.getInstance();
        dpcManager.clearnDATE_CACHE(); //清除cache

        //自定义背景绘制示例
        List<String> tmp = new ArrayList<>();
        tmp.add("2016-2-3"); //yyyy-M-d
        tmp.add("2016-2-1");
        tmp.add("2016-2-9");
        tmp.add("2016-2-10");
        tmp.add("2016-2-11");
        tmp.add("2016-2-12");
        dpcManager.setDecorBG(tmp); //预先设置日期背景 一定要在在开始设置

        myDatepicker.setDate(2016, 2); //设置日期

        myDatepicker.setMode(DPMode.NONE); //设置选择模式

        myDatepicker.setFestivalDisplay(false); //是否显示节日
        myDatepicker.setTodayDisplay(false); //是否高亮显示今天
        myDatepicker.setHolidayDisplay(false); //是否显示假期
        myDatepicker.setDeferredDisplay(false); //是否显示补休
        myDatepicker.setIsScroll(false); //是否允许滑动 false表示左右上下都不能滑动  单项设置上下or左右 你需要自己扩展
        myDatepicker.setIsSelChangeColor(true, getResources().getColor(R.color.font_white_one)); //设置选择的日期字体颜色,不然有的背景颜色和默认的字体颜色不搭

        myDatepicker.setLeftTitle(2 + "月"); //左上方text
        myDatepicker.setRightTitle(false); //是否签到
        myDatepicker.setOnClickSignIn(this); //点击签到事件

        //设置预先选中日期的背景颜色
        myDatepicker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(getResources().getColor(R.color.blue));
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 4F, paint);
            }
        });

    }

    @Override
    public void signIn() {
        //动态更新的时候必须  清除cache
        dpcManager.clearnDATE_CACHE(); //清除cache
        //重新设置日期
        List<String> tmp = new ArrayList<>();
        tmp.add("2016-2-20");
        tmp.add("2016-2-21");
        tmp.add("2016-2-22");
        tmp.add("2016-2-25");
        dpcManager.setDecorBG(tmp);

        myDatepicker.setDate(2016, 2);
        myDatepicker.setLeftTitle("2月");
        myDatepicker.setRightTitle(true);

        myDatepicker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(getResources().getColor(R.color.blue));
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 4F, paint);
            }
        });
        myDatepicker.invalidate(); //刷新
    }
}
