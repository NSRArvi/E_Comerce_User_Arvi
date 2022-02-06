package com.example.e_comerce_user.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class CustomBindingAdapters {
    @BindingAdapter(value = "app:setIcon")
    public static void setIconResource(ImageView imageView, int icon) {
        imageView.setImageResource(icon);
    }

    @BindingAdapter(value = "app:setImageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null) {
            Picasso.get().load(url).into(imageView);
        }
    }
}
