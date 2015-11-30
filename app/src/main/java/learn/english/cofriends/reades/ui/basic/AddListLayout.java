package learn.english.cofriends.reades.ui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;
import learn.english.cofriends.reades.R;
import learn.english.cofriends.reades.ui.basic.tools.UiUtils;
import learn.english.cofriends.reades.utils.BusUtils;

public class AddListLayout extends BaseListLayout {

    @Optional
    @InjectView(R.id.image_add)
    protected View mImageAdd;

    public AddListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        UiUtils.show(mImageAdd);
    }

    @Optional
    @OnClick(R.id.image_add)
    @SuppressWarnings("unused")
    void onAddClicked() {
        BusUtils.post(new ListAddActivity.AddEvent());
    }
}
