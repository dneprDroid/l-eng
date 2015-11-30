package learn.english.cofriends.reades.service;

import android.app.Service;

import dagger.Module;
import dagger.ObjectGraph;
import learn.english.cofriends.reades.AppModule;
import learn.english.cofriends.reades.MainApplication;
import learn.english.cofriends.reades.service.book.SavedBooksService;

@Module(addsTo = AppModule.class,
injects = {
        SavedBooksService.class
}, library = true, complete = false)
public final class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        this.mService = service;
    }

    public static ObjectGraph serviceScopeGraph(Service service) {
        return MainApplication.get(service).objectGraph()
                .plus(new ServiceModule(service));
    }
}