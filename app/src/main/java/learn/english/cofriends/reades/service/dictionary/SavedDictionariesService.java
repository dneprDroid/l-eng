package learn.english.cofriends.reades.service.dictionary;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import learn.english.cofriends.reades.entity.Dictionary;
import learn.english.cofriends.reades.utils.BundleUtils;
import learn.english.cofriends.reades.utils.BusUtils;
import learn.english.cofriends.reades.utils.ZipUtils;

public class SavedDictionariesService extends IntentService {

    private static final int LOAD_LIST = 0;
    private static final int LOAD_BY_ID = 1;
    public static final int SAVE = 2;
    public static final int DELETE = 3;
    private static final String EXTRA_TYPE = "extra_type";
    private static final String EXTRA_ID = "extra_id";
    private static Dictionary sCurrentDictionary;

    public SavedDictionariesService() {
        super(SavedDictionariesService.class.getSimpleName());

        setIntentRedelivery(true);
    }

    public static void loadList(Context context) {
        context.startService(new Intent(context, SavedDictionariesService.class)
                .putExtra(EXTRA_TYPE, LOAD_LIST));
    }

    public static void loadById(int dictionaryId, Context context) {
        context.startService(new Intent(context, SavedDictionariesService.class)
                .putExtra(EXTRA_TYPE, LOAD_BY_ID)
                .putExtra(EXTRA_ID, dictionaryId));
    }

    /**
     * Does action upon given dictionary.
     * @param context to use
     * @param dictionary to act upon
     * @param action to do. One of {@link #DELETE} or {@link #SAVE}
     */
    public static void actUpon(Context context, Dictionary dictionary, int action) {
        context.startService(new Intent(context, SavedDictionariesService.class)
                .putExtra(EXTRA_TYPE, action)
                .putExtras(BundleUtils.writeObject(Dictionary.class, dictionary)));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int type = intent.getIntExtra(EXTRA_TYPE, LOAD_LIST);

        switch (type) {
            case SAVE: {
                Dictionary dictionary = BundleUtils.fetchFromBundle(Dictionary.class, intent.getExtras());

                // save or link with saved languages in the database
                dictionary.setFromLanguage(dictionary.getFromLanguage().meFromDb());
                dictionary.setToLanguage(dictionary.getToLanguage().meFromDb());

                // extract zip to private data or SD card if there is no room
                String newPath = getApplicationContext().getFilesDir().getAbsolutePath() + "/";
                dictionary.setLoadedPath(ZipUtils.unzip(dictionary.getDbUrl(), newPath));

                // save to the database
                dictionary.save();

                BusUtils.post(new Dictionary.SavedEvent(dictionary));

                break;
            }

            case DELETE: {
                Dictionary dictionary = BundleUtils.fetchFromBundle(Dictionary.class, intent.getExtras()).meFromDb();
                // delete dictionary itself
                dictionary.delete();

                break;
            }

            case LOAD_LIST:
                loadAll();
                break;

            case LOAD_BY_ID:
                int dictionaryId = intent.getIntExtra(EXTRA_ID, 0);
                loadById(dictionaryId);
                break;
        }
    }

    private void loadAll() {
        // retrieve updated list of all dictionaries from the database
        List<Dictionary> dictionaries = Dictionary.listAll(Dictionary.class);
        BusUtils.postToUi(new Dictionary.ListLoadedEvent(dictionaries));
    }

    private void loadById(int dictionaryId) {
        // retrieve updated list of all dictionaries from the database
        Dictionary dictionary = Dictionary.fromDb(dictionaryId);
        BusUtils.postToUi(new Dictionary.LoadedEvent(dictionary));
    }

    public static Dictionary getCurrent() {
        return sCurrentDictionary;
    }

    public static void setCurrentDictionary(Dictionary dictionary) {
        sCurrentDictionary = dictionary;
    }
}
