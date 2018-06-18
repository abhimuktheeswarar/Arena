package com.abhi.spectacle.utilities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

public class ImageLoader {

    public static void loadImage(final ImageView imageView, final ConstraintLayout constraintLayout, String imageUrl) {
        GlideApp.with(imageView).asBitmap().load(imageUrl).dontTransform().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                @SuppressLint("DefaultLocale") String ratio = String.format("%d:%d", resource.getWidth(), resource.getHeight());
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.setDimensionRatio(imageView.getId(), ratio);
                constraintSet.applyTo(constraintLayout);
                imageView.setImageBitmap(resource);
            }
        });


    }


}
