package patrickengelkes.com.alleneune.array_adapters.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.objects.Fine;

/**
 * Created by patrickengelkes on 20/02/15.
 */
public class FineArrayAdapter extends ArrayAdapter<Fine> {

    private List<Fine> fineList;
    private Activity context;

    public FineArrayAdapter(Activity context, List<Fine> fineList) {
        super(context, R.layout.list_fine_layout, fineList);

        this.context = context;
        this.fineList = fineList;
    }

    //override this for list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_fine_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.fineNameTextView = (TextView) rowView.findViewById(R.id.fine_name_text_view);
            viewHolder.fineAmountTextView = (TextView) rowView.findViewById(R.id.fine_amount_text_view);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Fine fine = fineList.get(position);

        holder.fineNameTextView.setText(fine.getName());
        holder.fineAmountTextView.setText(fine.getShowAmount());

        return rowView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View spinner;

        LayoutInflater inflater = context.getLayoutInflater();
        spinner = inflater.inflate(R.layout.list_fine_layout, null);

        TextView fineNameTextView = (TextView) spinner.findViewById(R.id.fine_name_text_view);
        TextView fineAmountTextView = (TextView) spinner.findViewById(R.id.fine_amount_text_view);

        Fine fine = fineList.get(position);

        fineNameTextView.setText(fine.getName());
        fineAmountTextView.setText(fine.getShowAmount());

        return spinner;
    }

    //override this for spinner

    @Override
    public int getCount() {
        return fineList.size();
    }

    static class ViewHolder {
        protected TextView fineNameTextView;
        protected TextView fineAmountTextView;
    }
}
