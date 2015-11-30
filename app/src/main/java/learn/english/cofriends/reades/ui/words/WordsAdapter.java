package learn.english.cofriends.reades.ui.words;

import android.content.Context;
import android.view.View;

import learn.english.cofriends.reades.R;
import learn.english.cofriends.reades.entity.Word;
import learn.english.cofriends.reades.ui.basic.tools.BindableArrayAdapter;

public class WordsAdapter extends BindableArrayAdapter<Word> {

    public WordsAdapter(Context context) {
        super(context, R.layout.item_word);
    }

    @Override
    public void bindView(Word item, int position, View view) {
        WordItemView itemView = (WordItemView) view;
        itemView.setWord(item);
    }
}
