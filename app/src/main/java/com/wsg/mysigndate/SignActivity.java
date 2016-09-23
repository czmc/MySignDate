package com.wsg.mysigndate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.wsg.mysign.mydatepicker.DPDecor;
import com.wsg.mysign.mydatepicker.SignDatePicker;

import java.util.Arrays;
import java.util.List;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        final SignDatePicker picker = (SignDatePicker) findViewById(R.id.main_dp);
        picker.setDate(2016, 9);
        picker.setUpDate(Arrays.asList(new String[]{"2016-9-16", "2016-9-17", "2016-9-18"}));
        picker.setCancleDate(Arrays.asList(new String[]{"2016-9-19", "2016-9-21", "2016-9-22"}));
        picker.setFestivalDisplay(false);
        picker.setDeferredDisplay(false);
        picker.setHolidayDisplay(false);
        picker.setTodayDisplay(false);
        picker.setOnDateSelectedListener(new SignDatePicker.OnDateSelectedListener() {
            @Override
            public void onGoWok(List<String> date) {
                Toast.makeText(SignActivity.this, "添加上班日期:" + Arrays.toString(date.toArray()), Toast.LENGTH_SHORT).show();
                picker.getDateEnterSelected().addAll(date);
                picker.clearSelected();
                picker.invalidate();
            }

            @Override
            public void onCancelGoWok(List<String> date) {
                Toast.makeText(SignActivity.this, "添加休息日期:" + Arrays.toString(date.toArray()), Toast.LENGTH_SHORT).show();
                picker.getDateEnterSelected().removeAll(date);
                picker.getDateCancelSelected().addAll(date);
                picker.clearSelected();
                picker.invalidate();
            }
        });
    }
}
