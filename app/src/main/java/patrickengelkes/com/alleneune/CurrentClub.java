package patrickengelkes.com.alleneune;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by patrickengelkes on 06/02/15.
 */
@Singleton
public class CurrentClub {

    private String clubName;
    private int clubID;

    @Inject
    public CurrentClub() {}

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getClubID() {
        return clubID;
    }

    public void setClubID(int clubID) {
        this.clubID = clubID;
    }
}
