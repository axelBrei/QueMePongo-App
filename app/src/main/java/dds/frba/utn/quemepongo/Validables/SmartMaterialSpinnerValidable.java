package dds.frba.utn.quemepongo.Validables;

import android.view.View;
import android.widget.AdapterView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import dds.frba.utn.quemepongo.Utils.CustomOnItemSelectedListener;
import dds.frba.utn.quemepongo.Utils.Validable;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmartMaterialSpinnerValidable implements Validable {
    SmartMaterialSpinner spinner;
    CustomOnItemSelectedListener listener;

    public SmartMaterialSpinnerValidable(SmartMaterialSpinner spinner,CustomOnItemSelectedListener listener) {
        this.spinner = spinner;
        this.listener = listener;
    }

    @Override
    public Object getContent() {
        return spinner.getSelectedItem();
    }

    @Override
    public void setError(String error) {
        spinner.setErrorText(error);
    }

    @Override
    public void cleanErrorOnChange() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setErrorText(null);
                listener.onItemSelected(parent, view,position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setErrorText(null);
            }
        });
    }
}
