package learn.english.cofriends.reades.ui.dictionaries;

import com.squareup.otto.Subscribe;

import learn.english.cofriends.reades.R;
import learn.english.cofriends.reades.entity.Dictionary;
import learn.english.cofriends.reades.ui.books.BooksActivity;
import learn.english.cofriends.reades.ui.basic.ListAddActivity;

public class DictionariesActivity extends ListAddActivity {

    @SuppressWarnings("unused")
    @Subscribe
    public void onDictionarySelected(Dictionary.SelectedEvent event) {
        BooksActivity.start(event.getData(), this);
    }

    @Override
    protected int getDownloadViewId() {
        return R.layout.download_dictionaries_view;
    }

    @Override
    protected int getSavedViewId() {
        return R.layout.saved_dictionaries_view;
    }
}
