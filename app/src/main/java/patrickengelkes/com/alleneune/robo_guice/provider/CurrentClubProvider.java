package patrickengelkes.com.alleneune.robo_guice.provider;

import com.google.inject.Provider;

import patrickengelkes.com.alleneune.CurrentClub;

/**
 * Created by patrickengelkes on 06/02/15.
 */
public class CurrentClubProvider implements Provider<CurrentClub> {
    @Override
    public CurrentClub get() {
        return new CurrentClub();
    }
}
