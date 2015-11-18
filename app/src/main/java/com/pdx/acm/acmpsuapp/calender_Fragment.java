package com.pdx.acm.acmpsuapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

/**
 * calender_Fragment is the class managing the Calender drawer responsible of holding dates for
 * events users can look up.
 */
public class calender_Fragment extends Fragment implements CalendarPickerController {

    private String TAG = "com.pdx.acm.acmpsuapp";
    Toolbar mToolbar;
    AgendaCalendarView mAgendaCalendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calender_layout, container, false);
        ButterKnife.bind(rootView);

        mAgendaCalendarView = (AgendaCalendarView) rootView.findViewById(R.id.agenda_calendar_view);
        mToolbar = (Toolbar) rootView.findViewById(R.id.activity_toolbar);

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        List<CalendarEvent> eventList = new ArrayList<>();
        mockList(eventList);

        mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), this);

        return rootView;
    }

    @Override
    public void onDaySelected(DayItem dayItem) {
        Log.d(TAG, "Selected day: " + dayItem);
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
        Calendar startTime = event.getStartTime();
        Calendar endTime = event.getEndTime();
        int starthr = startTime.get(Calendar.HOUR_OF_DAY);
        int startmi = startTime.get(Calendar.MINUTE);
        int endhr = endTime.get(Calendar.HOUR_OF_DAY);
        int endmi = endTime.get(Calendar.MINUTE);
        Log.d(TAG, "Selected event: " + starthr + ":" + startmi + " " + endhr + ":" + endmi + ".");
    }

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 18);
        startTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.HOUR_OF_DAY, 20);
        endTime.set(Calendar.MINUTE, 0);
        CalendarEvent event = new CalendarEvent("ACM workshop by placeholder", "", "FAB-86-01",
                ContextCompat.getColor(getContext(), R.color.blue_dark), startTime, endTime, false);
        eventList.add(event);
    }
}

