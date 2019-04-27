package dds.frba.utn.quemepongo.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerArrayAdapter extends ArrayAdapter<String> {
    private Context context;

    public SpinnerArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public SpinnerArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    public SpinnerArrayAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, String spinnerName) {
        super(context, resource);
        super.add("Seleccione " + spinnerName);
        for (int i = 1; i < objects.size(); i++)
            super.insert(objects.get(i), i);

    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =super.getDropDownView(position, convertView, parent);
        TextView textView = (TextView) view;
        if(position == 0)
            textView.setTextColor(Color.GRAY);
        else
            textView.setTextColor(Color.BLACK);

        return view;
    }


}
