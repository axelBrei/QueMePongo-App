package dds.frba.utn.quemepongo.View.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import commons.validator.routines.EmailValidator;
import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.CustomComponents.AppText;
import me.grantland.widget.AutofitTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailFragment extends Fragment {
    private static final String KEY_EVENT_SERIALIZED = "EventSerialized";

    @BindView(R.id.EventDetailName)
        AutofitTextView nameTextView;
    @BindView(R.id.EventDetailTime)
        TextView timeTextView;
    @BindView(R.id.EventDetailDay)
        TextView dayTextView;
    @BindView(R.id.EventDetailFomality)
        AppText formalityTextView;
    @BindView(R.id.EventDetailFrecuency)
        AppText frecuencyTextView;
    @BindView(R.id.EventDetailLocation)
        AppText locationTextView;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    public static EventDetailFragment createFragment(Evento evento){
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_EVENT_SERIALIZED, evento);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this,view);

        Bundle bundle = getArguments();
        Evento evento = (Evento) bundle.getSerializable(KEY_EVENT_SERIALIZED);
        initView(evento);
        return view;
    }


    private void initView(Evento evento){
        if(evento != null){
            nameTextView.setText(evento.getNombre());
            formalityTextView.setText(evento.getFormalidad());
            frecuencyTextView.setText(evento.getFrecuencia());
            locationTextView.setText(evento.getNombreUbicacion());
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEEEE, MMMM dd");
            dayTextView.setText(dayFormat.format(evento.getDesde()));

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String time = timeFormat.format(evento.getDesde()) + " - " + timeFormat.format(evento.getHasta());
            timeTextView.setText(time);
        }
    }

    @OnClick(R.id.EventDetailDelete)
    void deleteEvent(View v){

    }

    @OnClick(R.id.EventDetailCloseFragment)
    void closeFragment(View v){

    }

}
