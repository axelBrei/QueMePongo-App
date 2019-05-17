package dds.frba.utn.quemepongo.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;

import java.util.List;

import androidx.annotation.Nullable;

public class SpinnerArrayAdapter<T> extends ArrayAdapter<T> implements SpinnerAdapter{
    private Context context;

    public SpinnerArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public SpinnerArrayAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    public SpinnerArrayAdapter(@NonNull Context context, int resource, @NonNull List<T> objects, String spinnerName) {
        super(context, resource);
        for (int i = 1; i < objects.size(); i++)
            super.insert(objects.get(i), i);

    }
}

