package com.zt.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by zhangteng on 2017/12/19.
 */

public class CalendarActivity extends BaseActivity {

    MaterialCalendarView calendarView;
    TextView dateTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mImmersionBar.statusBarColor(R.color.colorAccent).init();
        materialCalendar();
    }

    private void materialCalendar() {
        calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        dateTv = (TextView) findViewById(R.id.dateTv);
        //CalendarDay day=;
        List<CalendarDay> list = new ArrayList<>();
        list.add(CalendarDay.from(2017, 9, 18));
        list.add(CalendarDay.from(2017, 10, 18));
        list.add(CalendarDay.from(2017, 11, 18));
        //增加日期上的小圆点、周末的背景色
        calendarView.addDecorators(new EventDecorator(Color.GREEN, list), new HighlightWeekendsDecorator());
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
            }
        });
    }
}
