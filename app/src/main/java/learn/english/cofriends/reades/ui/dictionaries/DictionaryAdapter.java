package learn.english.cofriends.reades.ui.dictionaries;

import android.content.Context;
import android.view.View;

import com.squareup.picasso.Picasso;

import learn.english.cofriends.reades.R;
import learn.english.cofriends.reades.entity.Dictionary;
import learn.english.cofriends.reades.ui.basic.tools.BindableArrayAdapter;

public class DictionaryAdapter extends BindableArrayAdapter<Dictionary> {

    private final int mDetailsRes;
    private final int mSize;
    private final Picasso mPicasso;

    public DictionaryAdapter(Context context, int detailsRes, Picasso picasso) {
        super(context, R.layout.item_dictionary);

        mDetailsRes = detailsRes;
        mSize = (int) context.getResources().getDimension(R.dimen.item_icon_size);
        mPicasso = picasso;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getDictionaryId();
    }

    @Override
    public void bindView(Dictionary item, int position, View view) {
        DictionaryItemView itemView = (DictionaryItemView) view;
        itemView.bind(item, mPicasso, mDetailsRes, mSize);
    }
}
