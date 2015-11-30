package learn.english.cofriends.reades.ui.words;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import learn.english.cofriends.reades.R;
import learn.english.cofriends.reades.entity.Word;
import learn.english.cofriends.reades.service.SavedWordsService;
import learn.english.cofriends.reades.ui.basic.BaseActivity;
import learn.english.cofriends.reades.ui.basic.DrawerToggle;
import learn.english.cofriends.reades.utils.BusUtils;

public class WordsDrawerView extends LinearLayout {

    @InjectView(R.id.list_words)
    ListView listWords;

    @Inject
    DrawerToggle drawerToggle;

    private final WordsAdapter adapter;

    public WordsDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.words_drawer_view, this);

        adapter = new WordsAdapter(context);

        setBackgroundResource(R.color.white);
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ButterKnife.inject(this);

        listWords.setAdapter(adapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        BaseActivity.get(getContext()).inject(this);

        listWords.setEmptyView(findViewById(R.id.text_empty));

        post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });

        SavedWordsService.loadList(getContext());

        BusUtils.register(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        BusUtils.unregister(this);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onWordListLoaded(Word.ListLoadedEvent event) {
        adapter.replaceWith(event.getData());
    }

    @OnItemClick(R.id.list_words)
    @SuppressWarnings("unused")
    public void onWordClicked(int position) {
        Word word = (Word) listWords.getItemAtPosition(position);
        DefinitionDialogFactory.show(getContext(), word);
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onWordRemove(Word.RemoveEvent event) {
        SavedWordsService.delete(getContext(), event.getData());
    }
}
