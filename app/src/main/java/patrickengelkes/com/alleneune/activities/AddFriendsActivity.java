package patrickengelkes.com.alleneune.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import patrickengelkes.com.alleneune.ArrayAdapters.FriendsArrayAdapter;
import patrickengelkes.com.alleneune.ArrayAdapters.FriendsModel;
import patrickengelkes.com.alleneune.Objects.Friends;
import patrickengelkes.com.alleneune.Objects.User;
import patrickengelkes.com.alleneune.R;

public class AddFriendsActivity extends ListActivity {

    public final String TAG = AddFriendsActivity.class.getSimpleName();

    protected Button addFriendsToClubButton;

    private List<FriendsModel> friendsModelList = new ArrayList<FriendsModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        addFriendsToClubButton = (Button) findViewById(R.id.add_friends_to_club_button);
        addFriendsToClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<User> userList = new ArrayList<User>();
                for (FriendsModel model : friendsModelList) {
                    if (model.isSelected()) {
                        userList.add(model.getUser());
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] phoneNumbers = new String[3];
        phoneNumbers[0] = "11111";
        phoneNumbers[1] = "22222";
        phoneNumbers[2] = "44444";

        Friends friends = new Friends(phoneNumbers);
        if (friends.getRegisteredFriends()) {
            JSONObject jsonResponse = friends.getGetFriendsAbstractAnswer();
            try {
                JSONArray friendsArray = (JSONArray) jsonResponse.get("friends");
                for (User user : friends.getUserListFromJSONResponse(friendsArray)) {
                    FriendsModel friendsModel = new FriendsModel(user);
                    friendsModelList.add(friendsModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<FriendsModel> friendsArrayAdapter = new FriendsArrayAdapter(AddFriendsActivity.this, friendsModelList);
        setListAdapter(friendsArrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
//        Object o = this.getListAdapter().getItem(position);
        //FriendsModel friendsModel = friendsModelList.get(position);
        //friendsModel.setSelected(!friendsModel.isSelected());
        //Log.d(TAG, "Clicked on list item at position" + position);
        super.onListItemClick(l, v, position, id);
    }
}
