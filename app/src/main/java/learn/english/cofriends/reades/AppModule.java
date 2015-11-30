package learn.english.cofriends.reades;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import learn.english.cofriends.reades.data.DataModule;

@Module(includes = DataModule.class)
public final class AppModule {

    private final MainApplication mApplication;

    public AppModule(MainApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }
}