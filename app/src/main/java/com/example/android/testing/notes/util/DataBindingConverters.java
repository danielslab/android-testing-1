package com.example.android.testing.notes.util;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DataBindingConverters {

    @BindingAdapter({"app:onRefresh"})
    public static void bindOnRefresh(final SwipeRefreshLayout srl, final Runnable listener) {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                listener.run();
            }
        });
    }

    @BindingAdapter({"app:refreshing"})
    public static void bindRefreshing(final SwipeRefreshLayout srl, final boolean active) {
        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @BindingAdapter({"app:imageUrl"})
    public static void loadImage(ImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            view.setVisibility(View.VISIBLE);
            Glide.with(view.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .dontAnimate()
                    .into(view);
        } else {
            view.setVisibility(View.INVISIBLE);
            view.setImageDrawable(null);
        }
    }
}
