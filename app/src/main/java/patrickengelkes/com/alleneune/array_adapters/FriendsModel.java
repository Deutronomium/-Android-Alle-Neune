package patrickengelkes.com.alleneune.array_adapters;

import patrickengelkes.com.alleneune.entities.objects.User;

/**
 * Created by patrickengelkes on 28/11/14.
 */
public class FriendsModel {

    private User user;
    private boolean selected;

    public FriendsModel(User user) {
        this.user = user;
        selected = false;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
