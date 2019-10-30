package dds.frba.utn.quemepongo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dds.frba.utn.quemepongo.Model.WebServices.Response.Geocoding.NominatimGeocodeResponse;
import dds.frba.utn.quemepongo.R;
import me.grantland.widget.AutofitTextView;

public class LocationSpinnerAdapter extends ArrayAdapter implements Filterable {
    List<NominatimGeocodeResponse> list;
    List<NominatimGeocodeResponse> allItemsList;
    int layoutId;
    Context context;

    ListFilter listFilter = new ListFilter();
    
    @BindView(R.id.locationItemCellText)
        AutofitTextView title;

    public LocationSpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.list = new ArrayList<>();
        this.layoutId = resource;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public NominatimGeocodeResponse getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NominatimGeocodeResponse item = list.get(position);
        View v = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ButterKnife.bind(this, v);
        if(item != null && title != null){
            title.setText(item.toString());
        }

        return v;
    }


    public void addItems(List<NominatimGeocodeResponse> items){
        list.clear();
        list.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }


    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (allItemsList == null) {
                synchronized (lock) {
                    allItemsList = new ArrayList<>(list);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = allItemsList;
                    results.count = allItemsList.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<NominatimGeocodeResponse> matchValues = new ArrayList<>();

                for (NominatimGeocodeResponse dataItem : allItemsList) {
                    if (dataItem.toString().toLowerCase().startsWith(searchStrLowerCase)) {
                        matchValues.add(dataItem);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                list = (ArrayList<NominatimGeocodeResponse>)results.values;
            } else {
                list = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
