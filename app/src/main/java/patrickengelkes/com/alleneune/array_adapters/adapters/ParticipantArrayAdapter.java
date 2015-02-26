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
import patrickengelkes.com.alleneune.dialogs.DrinkPaymentDialog;
import patrickengelkes.com.alleneune.entities.objects.Drink;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 24/02/15.
 */
public class ParticipantArrayAdapter extends ArrayAdapter<User> {
    public static String TAG = ParticipantArrayAdapter.class.getSimpleName();
    private View.OnClickListener finePaymentButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Fine button was clicked!");
        }
    };
    private List<User> userList;
    private List<Drink> drinkList;
    private Activity context;
    private int eventID;

    public ParticipantArrayAdapter(Activity context, List<User> userList, int eventID,
                                   List<Drink> drinkList) {
        super(context, R.layout.list_participant_layout, userList);

        this.context = context;
        this.userList = userList;
        this.eventID = eventID;
        this.drinkList = drinkList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        User user = userList.get(position);

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.list_participant_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.userNameTextView = (TextView) rowView.findViewById(R.id.user_name_text_view);

            DrinkPaymentListener drinkPaymentListener = new DrinkPaymentListener(user.getId());

            viewHolder.addFinePaymentButton = (ImageView) rowView.findViewById(R.id.add_fine_payment_button);
            viewHolder.addFinePaymentButton.setOnClickListener(finePaymentButtonListener);

            viewHolder.addDrinkPaymentButton = (ImageView) rowView.findViewById(R.id.add_drink_payment_button);
            viewHolder.addDrinkPaymentButton.setOnClickListener(drinkPaymentListener);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.userNameTextView.setText(user.getUserName());


        return rowView;
    }

    static class ViewHolder {
        protected TextView userNameTextView;
        protected ImageView addDrinkPaymentButton;
        protected ImageView addFinePaymentButton;
    }

    class DrinkPaymentListener implements View.OnClickListener {

        int userID;

        public DrinkPaymentListener(int userID) {
            this.userID = userID;
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Drink button was clicked");
            DrinkPaymentDialog drinkPaymentDialog = new DrinkPaymentDialog(context, drinkList,
                    userID, eventID);
            drinkPaymentDialog.show();
        }
    }
}
