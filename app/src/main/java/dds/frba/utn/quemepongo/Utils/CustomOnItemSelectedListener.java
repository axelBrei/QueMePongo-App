package dds.frba.utn.quemepongo.Utils;

import android.view.View;
import android.widget.AdapterView;

public  class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private String listenerType;
    private OnItemSelectedCustom customListenner;


    public CustomOnItemSelectedListener(OnItemSelectedCustom customListenner, String listenerType) {
        this.listenerType = listenerType;
        this.customListenner = customListenner;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       customListenner.onItemSelectedCustom(parent,view,position,id, listenerType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public interface OnItemSelectedCustom{
        void onItemSelectedCustom(AdapterView<?> parent, View view, int position, long id, String spinnerType);
    }
}


