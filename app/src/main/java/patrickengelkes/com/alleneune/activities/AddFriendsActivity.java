package patrickengelkes.com.alleneune.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.inject.Inject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.array_adapters.adapters.FriendsArrayAdapter;
import patrickengelkes.com.alleneune.array_adapters.models.FriendsModel;
import patrickengelkes.com.alleneune.dialogs.ErrorDialog;
import patrickengelkes.com.alleneune.entities.controllers.ClubController;
import patrickengelkes.com.alleneune.entities.controllers.FriendsController;
import patrickengelkes.com.alleneune.entities.controllers.UserController;
import patrickengelkes.com.alleneune.entities.objects.User;
import patrickengelkes.com.alleneune.enums.ApiCall;
import roboguice.activity.RoboListActivity;

public class AddFriendsActivity extends RoboListActivity {
    public final String TAG = AddFriendsActivity.class.getSimpleName();
    protected Button addFriendsToClubButton;
    @Inject
    ClubController clubController;
    @Inject
    UserController userController;
    @Inject
    CurrentClub currentClub;
    private List<FriendsModel> friendsModelList = new ArrayList<FriendsModel>();
    private FriendsController friendsController = new FriendsController();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);

        List<String> phoneNumbers = new ArrayList<String>();
        phoneNumbers.add("11111");
        phoneNumbers.add("22222");
        phoneNumbers.add("44444");

        try {
            if (friendsController.getRegisteredFriends(phoneNumbers)) {
                JSONObject jsonResponse = friendsController.getGetFriendsAnswer();
                try {
                    JSONArray friendsArray = (JSONArray) jsonResponse.get("friends");
                    for (User user : userController.getUserListFromJSONResponse(friendsArray)) {
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

                ApiCall response = clubController.addFriendsToClub(phoneNumberList, currentClub.getId());

                if (response == ApiCall.SUCCESS) {
                    Intent clubHomeIntent = new Intent(AddFriendsActivity.this, ClubHomeActivity.class);
                    startActivity(clubHomeIntent);
                } else if (response == ApiCall.UNPROCESSABLE_ENTITY) {
                    AlertDialog alert = new ErrorDialog(AddFriendsActivity.this,
                            getString(R.string.club_does_not_exist_warning)).create();
                    alert.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
