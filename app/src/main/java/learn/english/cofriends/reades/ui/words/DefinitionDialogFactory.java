package learn.english.cofriends.reades.ui.words;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import org.dict.kernel.IAnswer;
import org.dict.kernel.IWordPosition;

import learn.english.cofriends.reades.R;
import learn.english.cofriends.reades.entity.Word;
import learn.english.cofriends.reades.service.SavedWordsService;
import learn.english.cofriends.reades.ui.basic.tools.BaseToast;
import learn.english.cofriends.reades.utils.PageSplitter;

public class DefinitionDialogFactory {

    public static void show(final Context context, IAnswer answer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final String key = answer.getKey();
        final String definition = answer.getDefinition();
        if (definition == null) {
            BaseToast.show(context, R.string.message_no_definition);

            // find adjacent words
            IWordPosition[] wordPositions = answer.getAdjacentWords().getWordPositions();
            final CharSequence[] adjacent = new CharSequence[wordPositions.length];
            int count = 0;
            for (IWordPosition position : wordPositions) {
                adjacent[count++] = position.getKey();
            }

            builder.setItems(adjacent, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PageSplitter.WordClickedListener listener = (PageSplitter.WordClickedListener) context;
                    listener.onWordClicked(adjacent[which]);
                    dialog.dismiss();
                }
            });
        } else {
            builder.setMessage(definition)
                    .setPositiveButton(R.string.title_add_to_my_words, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_POSITIVE) {
                                Word word = new Word(key, definition);
                                SavedWordsService.save(context, word);
                            }
                        }
                    });
        }

        builder.setNegativeButton(android.R.string.cancel, null)
                .setTitle(key)
                .create()
                .show();
    }

    public static void show(Context context, Word word) {
        new AlertDialog.Builder(context)
                .setNegativeButton(android.R.string.ok, null)
                .setMessage(word.getDefinition())
                .setTitle(word.getWord())
                .create()
                .show();
    }
}