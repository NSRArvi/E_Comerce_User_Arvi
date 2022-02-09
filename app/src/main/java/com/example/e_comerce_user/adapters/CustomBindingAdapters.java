package com.example.e_comerce_user.adapters;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomBindingAdapters {
    @BindingAdapter(value = "app:setIcon")
    public static void setIconResource(ImageView imageView, int icon) {
        imageView.setImageResource(icon);
    }
    @BindingAdapter(value = "app:setFormattedDate")
    public static void setFormattedDate(TextView textView, long date) {
        final String dateString = new SimpleDateFormat("dd/MM/yyyy HH:mm")
                .format(new Date(date));
        textView.setText(dateString);
    }

    @BindingAdapter(value = "app:setImageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if (url != null) {
            Picasso.get().load(url).into(imageView);
        }
    }
}
