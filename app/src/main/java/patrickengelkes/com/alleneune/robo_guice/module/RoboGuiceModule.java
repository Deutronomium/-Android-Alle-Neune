package patrickengelkes.com.alleneune.robo_guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import patrickengelkes.com.alleneune.CurrentClub;
import patrickengelkes.com.alleneune.CurrentUser;
import patrickengelkes.com.alleneune.robo_guice.provider.CurrentClubProvider;
import patrickengelkes.com.alleneune.robo_guice.provider.CurrentUserProvider;

/**
 * Created by patrickengelkes on 06/02/15.
 */
public class RoboGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CurrentUser.class).toProvider(CurrentUserProvider.class).in(Singleton.class);
        bind(CurrentClub.class).toProvider(CurrentClubProvider.class).in(Singleton.class);
    }
}
