package com.zt.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

/**
 * Created by zhangteng on 2017/12/19.
 */

public class HighlightWeekendsDecorator implements DayViewDecorator {

    private static final int color = Color.parseColor("#228BC34A");
    private final Calendar calendar = Calendar.getInstance();
    private final Drawable highlightDrawable;
    Context context;

    public HighlightWeekendsDecorator(Context context) {
        highlightDrawable = new ColorDrawable(color);
        this.context = context;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY;
    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.setBackgroundDrawable(highlightDrawable);
        //view.setSelectionDrawable(highlightDrawable);
        //view.setDaysDisabled(true);
        view.addSpan(new ForegroundColorSpan(Color.rgb(222, 223, 226)));

    }
}