package dds.frba.utn.quemepongo.View.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dds.frba.utn.quemepongo.Adapters.GenericrecyclerAdapter;
import dds.frba.utn.quemepongo.Controllers.EventosController;
import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.Utils.CustomEventDay;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.ActivityHelper;
import dds.frba.utn.quemepongo.View.Fragments.CalendarFragment;
import dds.frba.utn.quemepongo.View.Fragments.EventDetailFragment;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import me.grantland.widget.AutofitTextView;

public class EventsActivity extends QueMePongoActivity implements CalendarFragment.OnCalendarDaySelected {
    public static final int NEW_EVENT_REQUEST_CODE = 10;
    public static final int NEW_EVENT_RESULT_SUCCED = 200;
    public static final int NEW_EVENT_RESULT_FAIL = 404;
    public static final String KEY_EVENT_SERIALIZABLE = "EventoSerializable";

    CalendarFragment calendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        enableBackButton();
        setToolbarTitle("Eventos");
        calendarFragment = CalendarFragment.createFragment(this);
        ActivityHelper.showFragment(_activity, calendarFragment, R.id.EventActivityContainer);
        getUserEvents();
    }

    private void getUserEvents() {
        EventosController controller = new EventosController(_activity);

        controller.getEventos(
                (succed, obj) -> {
                    if(succed){
                        setEvents( (List<Evento>) obj);
                    }
                }
        );
    }

    private List<CustomEventDay> eventToCustomEvent(List<Evento> eventos){
        List<CustomEventDay> customEventDays = new ArrayList<>();
        for (Evento e :eventos) {
            customEventDays.add(new CustomEventDay(_activity, e));
        }
        return customEventDays;
    }

    private void setEvents(List<Evento> eventos){
        calendarFragment.setEvents(
                eventToCustomEvent(eventos)
        );
    }

    // ---------------- SCREEN CONFIG ---------------------
    @Override
    protected int getView() {
        return R.layout.activity_events;
    }

    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }
    // -------------- END SCREEN CONFIG -------------------


    @Override
    public void onClick(Evento evento) {
        evento.toCalendar();
    }


    @OnClick(R.id.EventActivityFabNewEvent)
    void onClickNewEvent() {
        ActivityHelper.startActivityForResult(_activity, NewEventActivity.class, NEW_EVENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_EVENT_REQUEST_CODE){
            if(resultCode == NEW_EVENT_RESULT_SUCCED){
                //TODO add to the list
                Bundle bundle = data.getExtras();
                Evento evento = (Evento) bundle.getSerializable(KEY_EVENT_SERIALIZABLE);
                calendarFragment.addEvent(evento);
            }else{

            }
        }
    }

    @Override
    public void onBackPressed() {
        if(calendarFragment.isDetailVisible()){
            FragmentManager fm = getSupportFragmentManager();
            EventDetailFragment fragment =
                    (EventDetailFragment) fm.findFragmentByTag(CalendarFragment.EVENT_DETAIL_FRAGMENT_TAG);
            ActivityHelper.removeFragmentWithAnimation(_activity, fragment);
        }else {
            super.onBackPressed();
        }
    }
}
