package com.wsg.mysign.mydatepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wsg.mysigndate.R;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 *  原版DatePicker
 */
public class SignDatePicker extends LinearLayout {
    private final TextView cancleWork;
    private final TextView goWork;
    private final LayoutParams cancelParam;
    private final LayoutParams goWorkParam;
    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器

    private MonthView monthView;// 月视图
    private TextView tvYear, tvMonth;// 年份 月份显示


    private SignDatePicker.OnDateSelectedListener onDateSelectedListener;// 日期多选后监听

    public SignDatePicker(Context context) {
        this(context, null);
    }

    public SignDatePicker(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();

        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // 标题栏根布局
        RelativeLayout rlTitle = new RelativeLayout(context);
        rlTitle.setBackgroundColor(mTManager.colorTitleBG());
        int rlTitlePadding = MeasureUtil.dp2px(context, 10);
        rlTitle.setPadding(rlTitlePadding, rlTitlePadding, rlTitlePadding, rlTitlePadding);

        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setBackgroundColor(mTManager.colorTitleBG());
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        // 标题栏子元素布局参数
        RelativeLayout.LayoutParams lpYear =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpYear.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams lpMonth =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpMonth.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout.LayoutParams lpEnsure =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpEnsure.addRule(RelativeLayout.CENTER_VERTICAL);
        lpEnsure.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        // --------------------------------------------------------------------------------标题栏
        // 年份显示
        tvYear = new TextView(context);
        tvYear.setText("2015");
        tvYear.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tvYear.setTextColor(mTManager.colorTitle());

        // 月份显示
        tvMonth = new TextView(context);
        tvMonth.setText("六月");
        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        tvMonth.setTextColor(mTManager.colorTitle());


        rlTitle.addView(tvYear, lpYear);
        rlTitle.addView(tvMonth, lpMonth);

        addView(rlTitle, llParams);

        // --------------------------------------------------------------------------------周视图
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(mTManager.colorTitle());
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

        // ------------------------------------------------------------------------------------月视图
        monthView = new MonthView(context);
        monthView.setIsSelChangeColor(true,mTManager.colorSelectingText());
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                Log.e("月",month+"");
                tvMonth.setText(mLManager.titleMonth()[month - 1]);
            }

            @Override
            public void onYearChange(int year) {
                Log.e("年",year+"");
                String tmp = String.valueOf(year);
                if (tmp.startsWith("-")) {
                    tmp = tmp.replace("-", mLManager.titleBC());
                }
                tvYear.setText(tmp);
            }

            @Override
            public void onAllChange(int year, int month) {

            }
        });
        monthView.setOnDateScrollChangeListener(new MonthView.OnDateScrollChangeListener() {
            @Override
            public void scrollLeft(int year, int month) {
                String str = "向左滑动=="+"年份="+year+"--月份=="+month;
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void scrollRight(int year, int month) {
                String str = "向右滑动=="+"年份="+year+"--月份=="+month;
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void scrollTop(int year, int month) {
                String str = "向上滑动=="+"年份="+year+"--月份=="+month;
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void scrollBottom(int year, int month) {
                String str = "向下滑动=="+"年份="+year+"--月份=="+month;
                Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
            }
        });
        addView(monthView, llParams);
        //年份显示
        cancleWork = new TextView(context);
        cancleWork.setText("取消上班");
        cancleWork.setClickable(true);
        cancleWork.setPadding(0,MeasureUtil.dp2px(getContext(),15),0,MeasureUtil.dp2px(getContext(),15));
        cancleWork.setBackgroundColor(getResources().getColor(R.color.colorPrimaryShape));
        cancleWork.setGravity(Gravity.CENTER);
        cancleWork.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        cancleWork.setTextColor(mTManager.colorBG());
        cancleWork.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDateSelectedListener) {
                    onDateSelectedListener.oncancelGoWok(monthView.getDateSelected());
                }
            }
        });
        // 月份显示
        goWork = new TextView(context);
        goWork.setText("打卡上班");
        goWork.setClickable(true);
        goWork.setGravity(Gravity.CENTER);
        goWork.setPadding(0,MeasureUtil.dp2px(getContext(),15),0,MeasureUtil.dp2px(getContext(),15));
        goWork.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        goWork.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        goWork.setTextColor(mTManager.colorBG());
        goWork.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onDateSelectedListener) {
                    onDateSelectedListener.onGoWok(monthView.getDateSelected());
                }
            }
        });
       LinearLayout opera =  new LinearLayout(getContext());
        opera.setOrientation(HORIZONTAL);
        cancelParam = new LayoutParams(0,WRAP_CONTENT);
        cancelParam.weight=1;
        goWorkParam= new LayoutParams(0,WRAP_CONTENT);
        goWorkParam.weight=1;
        opera.addView(cancleWork, cancelParam);
        opera.addView(goWork, goWorkParam);
        addView(opera);
    }
    public List getDateSelected(){
       return monthView.getDateSelected();
    }
    public List getDateEnterSelected(){
        return monthView.getEnterDateSelected();
    }
    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month);
    }

    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
        monthView.setDPMode(mode);
    }

    /**
     * 节日标识
     * @param isFestivalDisplay
     */
    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    /**
     * 今天标识
     * @param isTodayDisplay
     */
    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    /**
     * 假期标识
     * @param isHolidayDisplay
     */
    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    /**
     * 补休标识
     * @param isDeferredDisplay
     */
    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }



    /**
     * 设置多选监听器
     *
     * @param onDateSelectedListener ...
     */
    public void setOnDateSelectedListener(SignDatePicker.OnDateSelectedListener onDateSelectedListener) {
        if (monthView.getDPMode() != DPMode.MULTIPLE) {
            throw new RuntimeException(
                    "Current DPMode does not MULTIPLE! Please call setMode set DPMode to MULTIPLE!");
        }
        this.onDateSelectedListener = onDateSelectedListener;
    }

    public void setUpDate(List<String> strings) {
        monthView.getEnterDateSelected().addAll(strings);
    }


    /**
     * 日期多选监听器
     */
    public interface OnDateSelectedListener {
        void onGoWok(List<String> date);
        void oncancelGoWok(List<String> date);
    }

}
