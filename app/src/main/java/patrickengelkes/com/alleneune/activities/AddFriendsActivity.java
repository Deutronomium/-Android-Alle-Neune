package patrickengelkes.com.alleneune.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import patrickengelkes.com.alleneune.array_adapters.adapters.FriendsArrayAdapter;
import patrickengelkes.com.alleneune.array_adapters.models.FriendsModel;
import patrickengelkes.com.alleneune.entities.controllers.ClubController;
import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.FriendsController;

public class AddFriendsActivity extends ListActivity {

    public final String TAG = AddFriendsActivity.class.getSimpleName();

    protected Button addFriendsToClubButton;
    protected Intent clubIntent;

    private List<FriendsModel> friendsModelList = new ArrayList<FriendsModel>();
    private FriendsController friendsController = new FriendsController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        clubIntent = getIntent();

        addFriendsToClubButton = (Button) findViewById(R.id.add_friends_to_club_button);
        addFriendsToClubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> phoneNumberList = new ArrayList<String>();
                for (FriendsModel model : friendsModelList) {
                    if (model.isSelected()) {
                        phoneNumberList.add(model.getUser().getPhoneNumber());
                    }
                }

                Club club = clubIntent.getParcelableExtra("club");
                ClubController clubController = new ClubController(club);
                if (clubController.addFriendsToClub(phoneNumberList)) {
                    Intent clubHomeIntent = new Intent(AddFriendsActivity.this, ClubHomeActivity.class);
                    clubHomeIntent.putExtra("club", club);
                    startActivity(clubHomeIntent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("11111");
        phoneNumbers.add("22222");
        phoneNumbers.add("44444");

        try {
            if (friendsController.getRegisteredFriends(phoneNumbers)) {
                JSONObject jsonResponse = friendsController.getGetFriendsAnswer();
                try {
                    JSONArray friendsArray = (JSONArray) jsonResponse.get("friends");
                    for (User user : User.getUserListFromJSONResponse(friendsArray)) {
                        FriendsModel friendsModel = new FriendsModel(user);
                        friendsModelList.add(friendsModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
