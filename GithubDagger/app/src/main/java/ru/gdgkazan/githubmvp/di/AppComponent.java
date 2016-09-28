package ru.gdgkazan.githubmvp.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.screen.auth.AuthActivity;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesActivity;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughActivity;

/**
 * @author Artur Vasilov
 */
@Singleton
@Component(modules = {DataModule.class})
public interface AppComponent {

    void injectAuthActivity(AuthActivity authActivity);

    void injectRepositoriesActivity(RepositoriesActivity repositoriesActivity);

    void injectWalkthroughActivity(WalkthroughActivity walkthroughActivity);

    KeyValueStorage keyValueStorage();
}
