package patrickengelkes.com.alleneune;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by patrickengelkes on 06/02/15.
 */
@Singleton
public class CurrentClub {

    private String name;
    private int id;

    @Inject
    public CurrentClub() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
