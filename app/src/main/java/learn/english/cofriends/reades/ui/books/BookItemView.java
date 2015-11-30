package learn.english.cofriends.reades.ui.books;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import learn.english.cofriends.reades.R;
import learn.english.cofriends.reades.entity.Book;
import learn.english.cofriends.reades.ui.basic.CheckableRelativeLayout;
import learn.english.cofriends.reades.ui.basic.tools.CircleTransform;

public class BookItemView extends CheckableRelativeLayout {

    @InjectView(R.id.text_name)
    TextView textName;

    @InjectView(R.id.text_details)
    TextView textDetails;

    @InjectView(R.id.image)
    ImageView image;

    public BookItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isInEditMode()) {
            return;
        }

        ButterKnife.inject(this);
    }

    public void bind(Book book, Picasso picasso, int size) {
        textName.setText(book.getName());
        if (book.getSourceType() == Book.SourceType.LIBRARY) {
            textDetails.setText(book.getAuthor().getName());
            picasso.load(book.getImageUrl())
                    .resize(size, size)
                    .transform(new CircleTransform())
                    .centerCrop()
                    .into(image);
        } else {
            textDetails.setText(book.getFormatType().toString());
            image.setImageResource(R.drawable.ic_launcher);
        }
    }
}
