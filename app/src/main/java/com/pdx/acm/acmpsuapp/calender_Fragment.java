package com.pdx.acm.acmpsuapp;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

/**
 * calender_Fragment is the class managing the Calender drawer responsible of holding dates for
 * events users can look up.
 */
public class calender_Fragment extends Fragment implements CalendarPickerController {

    String TAG = "com.pdx.acm.acmpsuapp";
    Toolbar mToolbar;
    AgendaCalendarView mAgendaCalendarView;
    Date dateTime;
    Date dateTime2;

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
        try {
            dateTime = timeformat(event.getStartTime().get(Calendar.HOUR_OF_DAY) +
                    ":" + event.getStartTime().get(Calendar.MINUTE));
            dateTime2 = timeformat(event.getEndTime().get(Calendar.HOUR_OF_DAY) +
                    ":" + event.getEndTime().get(Calendar.MINUTE));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Dialog dialog = new Dialog(getContext(), R.style.popout_title);
        dialog.setContentView(R.layout.layout_popup);
        dialog.setTitle(event.getTitle());
        dialog.setCancelable(true);

        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        String eventtext = "Location: " + event.getLocation() + "\n" +
                "Time: " + dateTime + " " + dateTime2;
        text.setText(eventtext);

        Button button = (Button) dialog.findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        dialog.show();
    }

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 18);
        startTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.HOUR_OF_DAY, 20);
        endTime.set(Calendar.MINUTE, 0);
        CalendarEvent event = new CalendarEvent("Github workshop by placeholder", "", "FAB-86-01",
                ContextCompat.getColor(getContext(), R.color.blue_dark), startTime, endTime, false);
        eventList.add(event);
    }

    private static Date timeformat(String time) throws ParseException {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("HH:mm", Locale.US);
        simpledateformat.setLenient(false);
        return simpledateformat.parse(time);
    }
}

