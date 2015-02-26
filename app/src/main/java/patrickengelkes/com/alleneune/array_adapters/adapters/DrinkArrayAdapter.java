package patrickengelkes.com.alleneune.array_adapters.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.objects.Drink;

/**
 * Created by patrickengelkes on 14/02/15.
 */
public class DrinkArrayAdapter extends ArrayAdapter<Drink> {

    private List<Drink> drinkList;
    private Activity context;

    public DrinkArrayAdapter(Activity context, List<Drink> drinkList) {
        super(context, R.layout.list_drink_layout, drinkList);

        this.context = context;
        this.drinkList = drinkList;
    }

    //override this for list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_drink_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.drinkNameTextView = (TextView) rowView.findViewById(R.id.drink_name_text_view);
            viewHolder.drinkPriceTextView = (TextView) rowView.findViewById(R.id.drink_price_text_view);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Drink drink = drinkList.get(position);

        holder.drinkNameTextView.setText(drink.getName());
        holder.drinkPriceTextView.setText(drink.getShowPrice());

        return rowView;
    }

    //override this for the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View spinner;

        LayoutInflater inflater = context.getLayoutInflater();
        spinner = inflater.inflate(R.layout.list_drink_layout, null);

        TextView drinkNameTextView = (TextView) spinner.findViewById(R.id.drink_name_text_view);
        TextView drinkPriceTextView = (TextView) spinner.findViewById(R.id.drink_price_text_view);

        Drink drink = drinkList.get(position);
        drinkNameTextView.setText(drink.getName());
        drinkPriceTextView.setText(drink.getShowPrice());

        return spinner;
    }

    @Override
    public int getCount() {
        return drinkList.size();
    }

    static class ViewHolder {
        protected TextView drinkNameTextView;
        protected TextView drinkPriceTextView;
    }
}
