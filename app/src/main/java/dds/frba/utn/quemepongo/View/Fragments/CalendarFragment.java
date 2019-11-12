package dds.frba.utn.quemepongo.View.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dds.frba.utn.quemepongo.Adapters.GenericrecyclerAdapter;
import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.Utils.CustomEventDay;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import lombok.Setter;
import me.grantland.widget.AutofitTextView;


public class CalendarFragment extends Fragment implements OnDayClickListener, EventDetailFragment.EventChangeListener {
    public static final String EVENT_DETAIL_FRAGMENT_TAG = "EVENT_DETAIL_FRAGMENT";

    @BindView(R.id.CalendarFragmentCalendar)
        CalendarView calendarView;
    @BindView(R.id.CalendarFragmentEventsList)
        RecyclerView eventListRecycler;
    @BindView(R.id.CalendarFragmentEventCollapsable)
        ExpandableLinearLayout eventCollapsable;
    @BindView(R.id.CalendarFragmentEventCollapsableHeader)
        RelativeLayout collapsableHeader;
    @BindView(R.id.CalendarFragmentEventCollapsableHeaderIcon)
        ImageView collapsableHeaderIcon;


    @Setter
    OnCalendarDaySelected dayClickListener;
    List<CustomEventDay> evetos = new ArrayList<>();
    GenericrecyclerAdapter<CustomEventDay> adapter;
    EventDetailFragment eventDetailFragment;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment createFragment(OnCalendarDaySelected listener){
        CalendarFragment fragment = new CalendarFragment();
        fragment.setDayClickListener(listener);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this,v);

        eventCollapsable.setClosePosition(450);
        collapsableHeader.setOnClickListener( view -> toggleCollapasble());
        calendarView.setOnDayClickListener(this);
        eventListRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        eventListRecycler.setAdapter(getDaysAdapter());
        return v;
    }

    private void toggleCollapasble(){
        eventCollapsable.toggle();
        collapsableHeaderIcon.setImageDrawable(getResources().getDrawable(
                !eventCollapsable.isExpanded() ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_expand_less_black_24dp
        ));
    }

    public void setEvents(List<CustomEventDay> events){
        this.evetos = events;
        List<EventDay> days = new ArrayList<>();
        for (CustomEventDay e :events) {
            days.add(e.getEventDay());
        }
        calendarView.setEvents(days);

        adapter.replaceItems(getEventsForDay(Calendar.getInstance()));
    }

    public void addEvent(Evento eventDay){
        CustomEventDay event = new CustomEventDay(getActivity(), eventDay);
        this.evetos.add(event);
        setEvents(this.evetos);
    }

    private List<CustomEventDay> getEventsForDay(Calendar eventDay) {
        int day = eventDay.get(Calendar.DAY_OF_YEAR);
        List<CustomEventDay> aux = new ArrayList<>();
        for (CustomEventDay item :evetos) {
            if(item.getEventDay().getCalendar().get(Calendar.DAY_OF_YEAR) == day){
                aux.add(item);
            }
        }
        return aux;
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        List<CustomEventDay> eventDays = getEventsForDay(eventDay.getCalendar());
        adapter.replaceItems(eventDays);
    }


    private GenericrecyclerAdapter<CustomEventDay> getDaysAdapter(){
        adapter = new GenericrecyclerAdapter<CustomEventDay>(getContext(), R.layout.calendar_event_cell,null) {
            @Override
            public void fillView(View v, CustomEventDay item) {
                AutofitTextView title = v.findViewById(R.id.calendareventCellName);
                AutofitTextView time = v.findViewById(R.id.calendarEventCellTime);
                ConstraintLayout container = v.findViewById(R.id.CalendarCellContainer);
                title.setText(item.getEvento().getNombre());
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                time.setText(format.format(item.getEvento().getDesde()));


                container.setOnClickListener( view -> {
                            eventDetailFragment = EventDetailFragment.createFragment(item.getEvento(), CalendarFragment.this);
                            ActivityHelper.showFragmentWithSlideIn(
                                    (AppCompatActivity) getActivity(),
                                    eventDetailFragment,
                                    R.id.EventActivityContainer,
                                    EVENT_DETAIL_FRAGMENT_TAG
                            );
                        }
                );
            }
        };
        return adapter;
    }

    public boolean isDetailVisible(){
        return eventDetailFragment != null && eventDetailFragment.isVisible();
    }

    @Override
    public void onDelete(Evento evento) {
        for (CustomEventDay ev :evetos) {
            if(ev.getEvento().getId() == evento.getId()){
                adapter.removeItem(ev);
            }
        }
    }

    public interface OnCalendarDaySelected {
        void onClick(Evento evento);
    }
}
