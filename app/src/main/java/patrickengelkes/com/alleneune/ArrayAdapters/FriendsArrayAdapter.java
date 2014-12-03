package patrickengelkes.com.alleneune.ArrayAdapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.List;

import patrickengelkes.com.alleneune.Objects.User;
import patrickengelkes.com.alleneune.R;

/**
 * Created by patrickengelkes on 28/11/14.
 */
public class FriendsArrayAdapter extends ArrayAdapter<FriendsModel> {

    public static final String TAG = FriendsArrayAdapter.class.getSimpleName();

    private final List<FriendsModel> friendsList;
    private final Activity context;

    public FriendsArrayAdapter(Activity context, List<FriendsModel> friendsList) {
        super(context, R.layout.friends_list_view_layout, friendsList);

        this.context = context;
        this.friendsList = friendsList;
    }

    static class ViewHolder {
        protected TextView textView;
        protected CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Get the data item for this position
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.friends_list_view_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.displayNameTV);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkFriendCB);
            convertView.setTag(viewHolder);
            viewHolder.checkBox.setTag(friendsList.get(position));
        } else {
            ((ViewHolder) convertView.getTag()).checkBox.setTag(friendsList.get(position));
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        User user = friendsList.get(position).getUser();
        if (user.getFirstName() != null && user.getLastName() != null)  {
            holder.textView.setText(user.getFirstName() + " " + user.getLastName());
        } else if (user.getFirstName() != null) {
            holder.textView.setText(user.getFirstName());
        } else {
            holder.textView.setText(user.getUserName());
        }

        holder.checkBox.setChecked(friendsList.get(position).isSelected());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) view.getTag();
                FriendsModel element = (FriendsModel) holder.checkBox.getTag();
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkFriendCB);
                checkBox.setChecked(!checkBox.isChecked());
                element.setSelected(checkBox.isChecked());
                Log.e(TAG, "Lol I was clicked");
            }
        });
        return convertView;
    }
}
