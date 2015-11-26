package com.example.android.testing.notes.util;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DataBindingConverters {

//    @BindingAdapter({"app:error"})
//    public static void bindValidationError(TextInputLayout textInputLayout, int errorRes) {
//        if (errorRes != 0) {
//            textInputLayout.setError(textInputLayout.getResources().getString(errorRes));
//        } else {
//            textInputLayout.setError(null);
//        }
//    }
//
//    @BindingAdapter({"app:binding"})
//    public static void bindEditText(EditText view, final ObservableString observableString) {
//        if (view.getTag(R.id.binded) == null) {
//            view.setTag(R.id.binded, true);
//            view.addTextChangedListener(new TextWatcherAdapter() {
//                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    observableString.set(s.toString());
//                }
//            });
//        }
//        String newValue = observableString.get();
//        if (!view.getText().toString().equals(newValue)) {
//            view.setText(newValue);
//        }
//    }

    @BindingAdapter({"app:visibleOrGone"})
    public static void bindVisibleOrGone(View view, boolean b) {
        view.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({"app:visible"})
    public static void bindVisible(View view, boolean b) {
        view.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(view.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(view);
        } else {
            view.setImageDrawable(null);
        }
    }

    @BindingConversion
    public static String convertObservableStringToString(ObservableString s) {
        return s.get();
    }
}
