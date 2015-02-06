package patrickengelkes.com.alleneune.robo_guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import patrickengelkes.com.alleneune.CurrentUser;
import patrickengelkes.com.alleneune.robo_guice.provider.CurrentUserProvider;

/**
 * Created by patrickengelkes on 06/02/15.
 */
public class CurrentUserModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(CurrentUser.class).toProvider(CurrentUserProvider.class).in(Singleton.class);
    }
}
