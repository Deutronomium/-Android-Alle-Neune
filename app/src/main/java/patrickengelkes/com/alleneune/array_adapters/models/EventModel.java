package patrickengelkes.com.alleneune.array_adapters.models;

import java.util.ArrayList;
import java.util.List;

import patrickengelkes.com.alleneune.entities.objects.Event;
import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 12/02/15.
 */
public class EventModel {

    public final List<User> children = new ArrayList<User>();
    private Event event;

    public EventModel(Event event) {
        this.event = event;
    }
}
