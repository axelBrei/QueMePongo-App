package dds.frba.utn.quemepongo.Validables;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

import dds.frba.utn.quemepongo.Utils.Validable;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AutoCompleteTextValidable implements Validable {
    AppCompatAutoCompleteTextView autoCompleteTextView;
    TextInputLayout layout;

    public AutoCompleteTextValidable(AppCompatAutoCompleteTextView autoCompleteTextView, TextInputLayout layout) {
        this.autoCompleteTextView = autoCompleteTextView;
        this.layout = layout;
    }

    @Override
    public Object getContent() {
        return autoCompleteTextView.getText().toString();
    }

    @Override
    public void setError(String error) {
        layout.setError(error);
    }

    @Override
    public void cleanErrorOnChange() {
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
