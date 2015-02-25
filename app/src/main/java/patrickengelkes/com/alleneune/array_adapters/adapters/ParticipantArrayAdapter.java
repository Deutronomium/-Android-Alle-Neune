package patrickengelkes.com.alleneune.array_adapters.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 24/02/15.
 */
public class ParticipantArrayAdapter extends ArrayAdapter<User> {
    public static String TAG = ParticipantArrayAdapter.class.getSimpleName();

    private List<User> userList;
    private Activity context;

    public ParticipantArrayAdapter(Activity context, List<User> userList) {
        super(context, R.layout.list_participant_layout, userList);

        this.context = context;
        this.userList = userList;
    }

    static class ViewHolder {
        protected TextView userNameTextView;
        protected ImageView addDrinkPaymentButton;
        protected ImageView addFinePaymentButton;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_participant_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.userNameTextView = (TextView) rowView.findViewById(R.id.user_name_text_view);
            viewHolder.addFinePaymentButton = (ImageView) rowView.findViewById(R.id.add_fine_payment_button);
            viewHolder.addFinePaymentButton.setOnClickListener(finePaymentButtonListener);

            viewHolder.addDrinkPaymentButton = (ImageView) rowView.findViewById(R.id.add_drink_payment_button);
            viewHolder.addDrinkPaymentButton.setOnClickListener(drinkPaymentButtonListener);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        User user = userList.get(position);
        holder.userNameTextView.setText(user.getUserName());


        return rowView;
    }

    private View.OnClickListener drinkPaymentButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "Drink button was clicked!");

        }
    };

    private View.OnClickListener finePaymentButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Fine button was clicked!");
        }
    };
}
