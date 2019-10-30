package dds.frba.utn.quemepongo.Utils;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormValidator {
    List<Validable> viewList;
    String errorMessage;

    public FormValidator(String errorMessage) {
        viewList = new ArrayList<>();
        this.errorMessage = errorMessage;
    }

    public void addView(Validable v){
        viewList.add(v);
        v.cleanErrorOnChange();
    }

    public Boolean validate(){
        Boolean resp = true;
        for (Validable item :viewList) {
            Object content = item.getContent();
            if(content == null || String.valueOf(content).isEmpty()){
                item.setError(errorMessage);
                resp = false;
            }
        }
        return resp;
    }

}
