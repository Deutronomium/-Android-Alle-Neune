package patrickengelkes.com.alleneune.robo_guice.provider;

import com.google.inject.Provider;

import patrickengelkes.com.alleneune.CurrentUser;

/**
 * Created by patrickengelkes on 06/02/15.
 */
public class CurrentUserProvider implements Provider<CurrentUser> {
    @Override
    public CurrentUser get() {
        return new CurrentUser();
    }
}
