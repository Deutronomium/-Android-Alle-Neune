package patrickengelkes.com.alleneune.array_adapters.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.objects.Event;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 12/02/15.
 */
public class ExpandableEventAdapter extends BaseExpandableListAdapter {

    public LayoutInflater layoutInflater;
    public Activity activity;
    private List<Event> eventList;
    private HashMap<Event, List<User>> eventChildList;

    public ExpandableEventAdapter(Activity activity, List<Event> eventList, HashMap<Event, List<User>> eventChildList) {
        this.activity = activity;
        this.eventList = eventList;
        this.eventChildList = eventChildList;
        this.layoutInflater = this.activity.getLayoutInflater();
    }

    @Override
    public int getGroupCount() {
        return this.eventList.size();
    }

    @Override
    public int getChildrenCount(int eventPosition) {
        return this.eventChildList.get(eventList.get(eventPosition)).size();
    }

    @Override
    public Object getGroup(int eventPosition) {
        return this.eventList.get(eventPosition);
    }

    @Override
    public Object getChild(int eventPosition, int userPosition) {
        return this.eventChildList.get(this.eventList.get(eventPosition)).get(userPosition);
    }

    @Override
    public long getGroupId(int eventPosition) {
        return eventPosition;
    }

    @Override
    public long getChildId(int eventPosition, int userPosition) {
        return userPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int eventPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listrow_group, null);
        }

        Event event = (Event) getGroup(eventPosition);
        TextView eventNameTextView = (TextView) convertView.findViewById(R.id.name_text_view);
        eventNameTextView.setText(event.getName());

        return convertView;
    }

    @Override
    public View getChildView(int eventPosition, int userPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        final User user = (User) getChild(eventPosition, userPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listrow_event_details, null);
        }
        text = (TextView) convertView.findViewById(R.id.name_text_view);
        text.setText(user.getUserName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, user.getUserName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }
}
