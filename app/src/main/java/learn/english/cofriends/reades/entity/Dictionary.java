package learn.english.cofriends.reades.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.List;

import learn.english.cofriends.reades.service.DownloadService;
import learn.english.cofriends.reades.utils.BusUtils;

public class Dictionary extends SugarRecord<Dictionary> implements DownloadService.Loadable {

    @Expose
    @SerializedName("id")
    private final int dictionaryId;

    @Expose
    private Language fromLanguage;

    @Expose
    private Language toLanguage;

    @Expose
    private String dbUrl;

    private Dictionary(int dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    @SuppressWarnings("unused")
    public Dictionary() {
        this(0);
    }

    /**
     * @return dictionary ID returned by API, so by it we can work with API
     */
    public int getDictionaryId() {
        return dictionaryId;
    }

    @Override
    public String getName() {
        return String.format("%s - %s", fromLanguage.getName(), toLanguage.getName());
    }

    /**
     * @return download URL for API received entity or local path for database entry
     */
    public String getDbUrl() {
        return dbUrl;
    }

    @Override
    public String getDownloadUrl() {
        return getDbUrl();
    }

    @Override
    public void setLoadedPath(String path) {
        // we will write into the database the local path
        dbUrl = path;
    }

    public Language getFromLanguage() {
        return fromLanguage;
    }

    public Language getToLanguage() {
        return toLanguage;
    }

    public void setFromLanguage(Language fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public void setToLanguage(Language toLanguage) {
        this.toLanguage = toLanguage;
    }

    public Dictionary meFromDb() {
        Dictionary dictionary = Dictionary.fromDb(getDictionaryId());
        if (dictionary == null) {
            // there is no me in the database
            save();
            return this;
        }
        return dictionary;
    }

    public static Dictionary fromDb(int dictionaryId) {
        List<Dictionary> dictionaries = Dictionary.find(Dictionary.class, "DICTIONARY_ID = ?", Integer.toString(dictionaryId));
        if (dictionaries.isEmpty()) {
            return null;
        }
        return dictionaries.get(0);
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "dictionaryId=" + dictionaryId +
                ", fromLanguage=" + fromLanguage +
                ", toLanguage=" + toLanguage +
                ", dbUrl='" + dbUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dictionary that = (Dictionary) o;

        if (dictionaryId != that.dictionaryId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return dictionaryId;
    }

    public String getDbConfigPath() {
        return String.format("%s%s-%s", getDbUrl(), fromLanguage.getCode(), toLanguage.getCode());
    }

    public static class Event extends BusUtils.Event<Dictionary> {

        public Event(Dictionary object) {
            super(object);
        }
    }

    public static class SelectedEvent extends Event {

        public SelectedEvent(Dictionary object) {
            super(object);
        }
    }

    public static class SavedEvent extends Event {

        public SavedEvent(Dictionary object) {
            super(object);
        }
    }

    public static class ListLoadedEvent extends BusUtils.Event<List<Dictionary>> {

        public ListLoadedEvent(List<Dictionary> dictionaries) {
            super(dictionaries);
        }
    }

    public static class LoadedEvent extends Event {

        public LoadedEvent(Dictionary dictionary) {
            super(dictionary);
        }
    }
}
