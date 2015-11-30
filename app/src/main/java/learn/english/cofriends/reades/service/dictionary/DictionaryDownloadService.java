package learn.english.cofriends.reades.service.dictionary;

import android.content.Context;

import learn.english.cofriends.reades.entity.Dictionary;
import learn.english.cofriends.reades.service.DownloadService;

public class DictionaryDownloadService extends DownloadService {

    public static void start(Context context, Loadable loadable) {
        DownloadService.start(context, loadable, DictionaryDownloadService.class);
    }

    @Override
    public void onLoaded(Loadable loadable) {
        if (loadable instanceof Dictionary) {
            SavedDictionariesService.actUpon(this, (Dictionary) loadable, SavedDictionariesService.SAVE);
        }
    }
}
