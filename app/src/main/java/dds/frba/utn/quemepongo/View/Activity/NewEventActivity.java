package dds.frba.utn.quemepongo.View.Activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.builders.DatePickerBuilder;
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import dds.frba.utn.quemepongo.Adapters.LocationSpinnerAdapter;
import dds.frba.utn.quemepongo.Controllers.EventosController;
import dds.frba.utn.quemepongo.Controllers.GoogleGeocodingController;
import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.Guardarropa;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Evento.NewEventResponse;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Geocoding.NominatimGeocodeResponse;
import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.Utils.CustomListenners.OnCompleteListenerWithStatus;
import dds.frba.utn.quemepongo.Utils.CustomOnItemSelectedListener;
import dds.frba.utn.quemepongo.Utils.FormValidator;
import dds.frba.utn.quemepongo.Validables.AutoCompleteTextValidable;
import dds.frba.utn.quemepongo.Validables.SmartMaterialSpinnerValidable;
import dds.frba.utn.quemepongo.View.CustomComponents.AppText;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;
import dds.frba.utn.quemepongo.ViewModel.NewEventViewModel;

public class NewEventActivity extends QueMePongoActivity implements CustomOnItemSelectedListener.OnItemSelectedCustom {
    @BindView(R.id.NewEventName)
        AppText eventName;
    @BindView(R.id.NewEventFormality)
        SmartMaterialSpinner formalitySpinner;
    @BindView(R.id.NewEventFrecuency)
        SmartMaterialSpinner frecuencySpinner;
    @BindView(R.id.NewEventSince)
        AppText eventSince;
    @BindView(R.id.NewEventUntil)
        AppText eventUntil;
    @BindView(R.id.NewEventLocation)
        AppCompatAutoCompleteTextView locationEditText;
    @BindView(R.id.LocationEditTextLayout)
        TextInputLayout locationLayout;
    @BindView(R.id.NewEventGuardarropa)
        SmartMaterialSpinner<Guardarropa> guardarropaSpinner;

    @BindView(R.id.NewEventSendButton)
        ActionProcessButton sendButton;

    NewEventViewModel viewModel;
    LocationSpinnerAdapter adapter;
    FormValidator validator;
    EventosController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarTitle("Crear Evento");
        enableBackButton();

        viewModel = ViewModelProviders.of(_activity).get(NewEventViewModel.class);

        initSpinners();
        initCalendarButtons();
        initLocationSearchable();
        initValidator();
        controller = new EventosController(_activity);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setName(eventName.getText());
                if(viewModel.getLatitud() == null){
                    locationLayout.setError("Debe seleecionar una direccion para poder continuar");
                }else if(validator.validate()){
                    Evento evento = viewModel.buildEvent();
                    controller.crearEvento(viewModel.buildEvent(), new OnCompleteListenerWithStatus() {
                        @Override
                        public void onComplete(Boolean succed, Object obj) {
                            if(succed){
                                NewEventResponse response = (NewEventResponse) obj;
                                evento.setId(Long.parseLong(response.getIdEvento()));
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(EventsActivity.KEY_EVENT_SERIALIZABLE, evento);
                                intent.putExtras(bundle);
                                setResult(EventsActivity.NEW_EVENT_RESULT_SUCCED, intent);
                            }else{
                                setResult(EventsActivity.NEW_EVENT_RESULT_FAIL);
                            }
                            finish();
                        }
                    });
                }
            }
        });
    }

    private void initValidator(){
        validator = new FormValidator("El campo es requerido");
        validator.addView(eventName);
        validator.addView(eventSince);
        validator.addView(eventUntil);
        validator.addView(new AutoCompleteTextValidable(locationEditText,locationLayout));
        validator.addView(new SmartMaterialSpinnerValidable(formalitySpinner, new CustomOnItemSelectedListener(this, "Formailidad")));
        validator.addView(new SmartMaterialSpinnerValidable(frecuencySpinner, new CustomOnItemSelectedListener(this, "Frecuencia")));
        validator.addView(new SmartMaterialSpinnerValidable(guardarropaSpinner, new CustomOnItemSelectedListener(this, "Guardarropa")));

    }

    private void initSpinners(){
        frecuencySpinner = findViewById(R.id.NewEventFrecuency);

        formalitySpinner.setItem(Arrays.asList("Formal", "Informal"));
        frecuencySpinner.setItem(Arrays.asList("Anual", "Mensual", "Semanal", "Diario", "Sin repetición"));
        QueMePongo app = (QueMePongo) getApplication();
        guardarropaSpinner.setItem(app.getGuardarropas().getValue());
    }

    private void initCalendarButtons() {
        eventSince.setOnClickListener( v -> {
            openDatePicker(calendar -> {
                Calendar sinse = calendar.get(0);
                openTimePicker((view, hourOfDay, minute) -> {
                    sinse.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    sinse.set(Calendar.MINUTE, minute);
                    eventSince.setText(calendarToString(sinse));
                    viewModel.setSince(sinse);
                });
            });
        });

        eventUntil.setOnClickListener( v -> {
            openDatePicker( calendar -> {
                Calendar until = calendar.get(0);
                openTimePicker((view, hourOfDay, minute) -> {
                    until.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    until.set(Calendar.MINUTE, minute);
                    eventUntil.setText(calendarToString(until));
                    viewModel.setUntil(until);
                });
            });
        });
    }

    private void initLocationSearchable() {
        GoogleGeocodingController controller = new GoogleGeocodingController(_activity);
        adapter = new LocationSpinnerAdapter(_activity, R.layout.location_item_cell);
        locationEditText.setAdapter(adapter);

        locationEditText.setThreshold(3);
        locationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 3){
                    controller.fetchAddressDetails(s.toString(), (succed, obj) -> {
                        if(succed){
                            adapter.addItems( (List<NominatimGeocodeResponse>) obj);
                        }
                    });
                }
            }
        });
        locationEditText.setOnItemClickListener(
                (parent, view, position, id) -> viewModel.setLocation( (NominatimGeocodeResponse) parent.getItemAtPosition(position) )
        );
    }

    // ---------------- SCREEN CONFIG ---------------------
    @Override
    protected int getView() {
        return R.layout.activity_new_event;
    }
    @Override
    protected boolean enableToolbarSpinner() {
        return false;
    }

    // -------------- END SCREEN CONFIG -------------------

    private void openDatePicker(OnSelectDateListener listener) {
        Calendar threeMonths = Calendar.getInstance();
        Calendar yesterday = (Calendar) threeMonths.clone();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        threeMonths.add(Calendar.MONTH, 3);
        new DatePickerBuilder(_activity,listener)
                .setDate(Calendar.getInstance())
                .setPickerType(CalendarView.ONE_DAY_PICKER)
                .setMinimumDate(yesterday)
                .setMaximumDate(threeMonths)
                .build()
                .show();
    }

    private void openTimePicker(TimePickerDialog.OnTimeSetListener listener) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(_activity, listener, hour, minute, true);
        dialog.setTitle("Selecciona la hora");
        dialog.show();
    }

    private String calendarToString(Calendar calendar){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
        return format.format(calendar.getTime());
    }

    @Override
    public void onItemSelectedCustom(AdapterView<?> parent, View view, int position, long id, String spinnerType) {
        Object val = parent.getItemAtPosition(position);
        switch (spinnerType){
            case "Frecuencia":{
                viewModel.setFrecuency( (String) val);
                break;
            }
            case "Formailidad": {
                viewModel.setFormalidad( (String) val);
                break;
            }
            case "Guardarropa": {
                Guardarropa g = (Guardarropa) parent.getItemAtPosition(position);
                viewModel.setIdGuardarropa(g.getId());
                break;
            }
        }
    }
}
