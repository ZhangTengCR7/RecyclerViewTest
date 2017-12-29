package com.zt.calendar;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.zt.BaseActivity;
import com.zt.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangteng on 2017/12/19.
 */

public class CalendarActivity extends BaseActivity implements OnChartValueSelectedListener {

    MaterialCalendarView calendarView;
    TextView dateTv;
    private PieChart mChart;
    private Typeface tf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mImmersionBar.statusBarColor(R.color.colorAccent).init();
        materialCalendar();
        //initPieChart();
    }

    private void materialCalendar() {
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        dateTv = (TextView) findViewById(R.id.dateTv);

        //允许左右滑动
        calendarView.setPagingEnabled(true);

        calendarView.setTileHeight(0);
        //隐藏默认title
        calendarView.setTopbarVisible(false);
        //设置星期文字的样式
        calendarView.setWeekDayTextAppearance(R.style.asd);
        //设置星期文字
        calendarView.setWeekDayLabels(R.array.weekLables);
        //设置不显示其他月份日期
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_NONE);
        //默认选中当天日期
        calendarView.setSelectedDate(new Date());
        //设置textview显示日期
        dateTv.setText(calendarView.getCurrentDate().getYear() + "-" + (calendarView.getCurrentDate().getMonth() + 1));
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONTH)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();
        monthChange(2017, 11);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(CalendarActivity.this, date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDay(), Toast.LENGTH_SHORT).show();
            }
        });

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                dateTv.setText(date.getYear() + "-" + (date.getMonth() + 1));
                monthChange(date.getYear(), date.getMonth());
            }
        });
    }

    private void monthChange(int year, int month) {
        Random random = new Random();
        List<DateBean> list = new ArrayList<>();
        DateBean bean;
        for (int i = 0; i < 22; i++) {
            bean = new DateBean();
            bean.setYear(year);
            bean.setMonth(month);
            bean.setDay(i);
            bean.setFlag(random.nextBoolean());
            list.add(bean);
        }
        List<CalendarDay> calendarList = new ArrayList<>();
        for (DateBean bean1 : list) {
            if (bean1.isFlag()) {
                calendarList.add(CalendarDay.from(bean1.getYear(), bean1.getMonth(), bean1.getDay()));
            }
        }
        calendarView.removeDecorators();
        //增加日期上的小圆点、周末的背景色
        calendarView.addDecorators(new EventDecorator(Color.GREEN, calendarList), new HighlightWeekendsDecorator());


        initPieChart(calendarList.size(), list.size());
    }

    private void initPieChart(int size, int total) {
        mChart = (PieChart) findViewById(R.id.piechart);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mChart.setCenterTextTypeface(tf);
        // mChart.setCenterText(generateCenterSpannableText());  //饼图中间的文字
        mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
        mChart.setDrawHoleEnabled(false);//中间是否是空心
        // mChart.setHoleColor(Color.WHITE);
        //mChart.setTransparentCircleColor(Color.WHITE);
        // mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);
        mChart.setDrawCenterText(true);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setEntryLabelColor(Color.RED);//设置饼图里文字颜色  不包括百分比
        setData(size, total);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);  //是否显示解释
    }

    private void setData(int size, int total) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(size, "已出勤", 0));
        entries.add(new PieEntry(total - size, "没出勤", 7));
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(1f);  //设置扇形之间的空间
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);  //百分比显示在图外还是图内

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.RED);
        data.setValueTypeface(tf);
        mChart.setData(data);
        //mChart.highlightValue(null);
        mChart.invalidate();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null) {
            return;
        }
        Toast.makeText(this, "" + e.getData(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }
}
