package patrickengelkes.com.alleneune.array_adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.entities.objects.Event;

/**
 * Created by patrickengelkes on 23/01/15.
 */
public class EventsArrayAdapter extends ArrayAdapter<Event> {

    private List<Event> eventList;
    private Activity context;

    public EventsArrayAdapter(Activity context, List<Event> eventList) {
        super(context, R.layout.event_list_view_layout, eventList);

        this.context = context;
        this.eventList = eventList;
    }

    static class ViewHolder {
        protected TextView eventNameTextView;
        protected TextView eventDateTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.event_list_view_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.eventNameTextView = (TextView) rowView.findViewById(R.id.event_name_text_view);
            viewHolder.eventDateTextView = (TextView) rowView.findViewById(R.id.event_date_text_view);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Event event = eventList.get(position);
        String eventName = event.getEventName();
        String eventDate = event.getEventDate();

        holder.eventNameTextView.setText(eventName);
        holder.eventDateTextView.setText(eventDate);

        return rowView;
    }
}
