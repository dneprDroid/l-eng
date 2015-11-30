package learn.english.cofriends.reades.ui.basic;

import com.cocosw.undobar.UndoBarController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import learn.english.cofriends.reades.AppModule;
import learn.english.cofriends.reades.ui.basic.tools.ContextMenuController;
import learn.english.cofriends.reades.ui.books.BooksActivity;
import learn.english.cofriends.reades.ui.dictionaries.DictionariesActivity;
import learn.english.cofriends.reades.ui.read.PageView;
import learn.english.cofriends.reades.ui.read.ReadActivity;
import learn.english.cofriends.reades.ui.words.WordsDrawerView;

@Module(addsTo = AppModule.class,
injects = {
        BaseActivity.class,
        DictionariesActivity.class,
        BooksActivity.class,
        ReadActivity.class,
        PageView.class,
        WordsDrawerView.class
}, library = true, complete = false)
public final class ActivityModule {

    private final BaseActivity mBaseActivity;

    public ActivityModule(BaseActivity activity) {
        this.mBaseActivity = activity;
    }

    @Provides
    @Singleton
    DrawerToggle provideDrawerToggle() {
        return DrawerToggle.fromActivity(mBaseActivity);
    }

    @Provides
    @Singleton
    UndoBarController.UndoBar provideUndoBar() {
        return new UndoBarController.UndoBar(mBaseActivity);
    }

    @Provides
    @Singleton
    ContextMenuController provideMenuController() {
        return new ContextMenuController();
    }
}