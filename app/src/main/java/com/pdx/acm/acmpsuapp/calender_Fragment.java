package com.pdx.acm.acmpsuapp;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.text.ParseException;
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
    private Context context;
    private String dateTime;
    private String dateTime2;
    protected Toolbar mToolbar;
    protected AgendaCalendarView mAgendaCalendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.calender_layout, container, false);
        ButterKnife.bind(rootView);
        context = getContext();

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
        Log.d(TAG, "Selected day: " + event);
        try {
            dateTime = timeformat(event.getStartTime().get(Calendar.HOUR_OF_DAY),
                    event.getStartTime().get(Calendar.MINUTE));
            dateTime2 = timeformat(event.getEndTime().get(Calendar.HOUR_OF_DAY),
                    event.getEndTime().get(Calendar.MINUTE));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Dialog dialog = new Dialog(context, R.style.popout_title);
        dialog.setContentView(R.layout.layout_popup);
        dialog.setTitle(event.getTitle());
        dialog.setCancelable(true);

        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        StringBuilder eventdefine = new StringBuilder();
        eventdefine.append("Location: ")
                .append(event.getLocation())
                .append("\nTime: ")
                .append(dateTime)
                .append(" to ")
                .append(dateTime2)
                .append("\n")
                .append(event.getDescription());
        text.setText(eventdefine);

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
        CalendarEvent event = new CalendarEvent("Github workshop by placeholder",
                "Git and other version control systems are an essential tool for anybody within" +
                        " Computer Science. As a student, you can use version control to backup" +
                        " and track your changes to homework. If you realize you have gone down" +
                        " the wrong path, git makes it easy to turn back time and return to a" +
                        " previous version of it. Furthermore, in the workforce version control" +
                        " will be the core method used to collaborate with your team on a daily" +
                        " basis. It cannot hurt to have this skill on your resume now!" +
                        " This workshop introduces you to the world of version control using git" +
                        " and helps you start exploring.", "FAB-86-01",
                ContextCompat.getColor(context, R.color.blue_dark), startTime, endTime, false);
        eventList.add(event);
    }

    private static String timeformat(int hours, int minutes) throws ParseException {
        return String.format("%02d:%02d", hours, minutes);
    }
}

