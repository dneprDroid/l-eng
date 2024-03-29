package learn.english.cofriends.reades.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.otto.Subscribe;

import learn.english.cofriends.reades.R;
import learn.english.cofriends.reades.entity.Book;
import learn.english.cofriends.reades.entity.Dictionary;
import learn.english.cofriends.reades.service.book.SavedBooksService;
import learn.english.cofriends.reades.service.dictionary.SavedDictionariesService;
import learn.english.cofriends.reades.ui.dictionaries.DictionariesActivity;
import learn.english.cofriends.reades.ui.read.ReadActivity;
import learn.english.cofriends.reades.utils.LocalStorage;

public class SplashActivity extends ActionBarActivity {

    private Dictionary mLoadedDictionary;
    private Book mLoadedBook;

    private StartupMode mMode = StartupMode.HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.splash_activity);

        int bookId = LocalStorage.INSTANCE.getInt(getString(R.string.key_book_id));
        int dictionaryId = LocalStorage.INSTANCE.getInt(getString(R.string.key_dictionary_id));
        if (bookId != 0) {
            mMode = StartupMode.CONTINUE_READING;
            SavedDictionariesService.loadById(dictionaryId, SplashActivity.this);
            SavedBooksService.loadById(bookId, SplashActivity.this);
        }

		// Start timer and launch main activity
		IntentLauncher launcher = new IntentLauncher();
		launcher.start();
	}

    @SuppressWarnings("unused")
    @Subscribe
    public void onDictionaryLoaded(Dictionary.LoadedEvent event) {
        mLoadedDictionary = event.getData();
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onBooksLoaded(Book.LoadedEvent event) {
        mLoadedBook = event.getData();
    }

    private void startUp() {
        if (mLoadedBook != null && mLoadedDictionary != null) {
            ReadActivity.start(mLoadedBook, mLoadedDictionary, this);
        } else {
            startActivity(new Intent(this, DictionariesActivity.class));
        }
        finish();
    }

	private class IntentLauncher extends Thread {
		@Override
		/**
		 * Sleep for some time and than start new activity.
		 */
		public void run() {
			try {
				// Sleeping
				Thread.sleep(mMode.mTimeOut);
			} catch (InterruptedException e) {
				// Error
			}

			// Start main activity
            startUp();
		}
	}

    private enum StartupMode {

        HOME(700l), CONTINUE_READING(1000l);

        private final long mTimeOut;

        private StartupMode(long timeOut) {
            mTimeOut = timeOut;
        }
    }
}