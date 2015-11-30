package learn.english.cofriends.reades.utils;

import com.loopj.android.http.AsyncHttpClient;

public class HttpUtils {

    private final static AsyncHttpClient CLEAR_CLIENT = new AsyncHttpClient();

    public static AsyncHttpClient getClient() {
        return CLEAR_CLIENT;
    }
}