package arturvasilov.udacity.nanodegree.popularmoviesdatabinding.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import arturvasilov.udacity.nanodegree.popularmoviesdatabinding.R;

/**
 * @author Artur Vasilov
 */
public class LoadingDialog extends DialogFragment {

    private static final String TAG = LoadingDialog.class.getSimpleName();
    private static final String TEXT_KEY = "text";

    @NonNull
    public static LoadingDialog create(@NonNull String text) {
        Bundle bundle = new Bundle();
        bundle.putString(TEXT_KEY, text);
        LoadingDialog dialog = new LoadingDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void show(@NonNull FragmentManager manager) {
        if (manager.findFragmentByTag(TAG) != null) {
            return;
        }
        show(manager, TAG);
    }

    public void cancel() {
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String text = getArguments().getString(TEXT_KEY, getString(R.string.loading_progress));
        return new MaterialDialog.Builder(getActivity())
                .progress(true, 0)
                .title(R.string.please_wait)
                .content(text)
                .build();
    }
}
