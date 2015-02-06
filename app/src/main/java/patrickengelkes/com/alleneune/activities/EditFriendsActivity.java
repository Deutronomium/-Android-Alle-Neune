package patrickengelkes.com.alleneune.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.inject.Inject;

import org.json.JSONException;

import java.util.List;

import patrickengelkes.com.alleneune.entities.objects.Club;
import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.controllers.ClubController;
import patrickengelkes.com.alleneune.entities.controllers.FriendsController;
import roboguice.activity.RoboListActivity;

public class EditFriendsActivity extends RoboListActivity {
    @Inject
    ClubController clubController;

    protected List<String> userNames;
    protected ListView listView;
    protected ArrayAdapter<String> arrayAdapter;
    protected FriendsController friendsController;
    protected Club club;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);
        this.friendsController = new FriendsController();
        this.club = getIntent().getParcelableExtra("club");

        //get list view from the activity
        listView = getListView();

        userNames = clubController.getUserByClub("TestClub");

        //set the adapter
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, userNames);
        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.equals(listView)) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle(userNames.get(info.position));
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                String itemToRemove = userNames.get(info.position);
                try {
                    friendsController.removeFriendFromClub(this.club.getClubName(), itemToRemove);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayAdapter.remove(itemToRemove);
                arrayAdapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);

    }
}
