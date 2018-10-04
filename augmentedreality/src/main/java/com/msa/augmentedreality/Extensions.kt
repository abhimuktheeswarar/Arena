package com.msa.augmentedreality

import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class SpectacleGlideModule : AppGlideModule()

fun ImageView.loadUrl(url: String) {
    GlideApp.with(context)
            .load(url)
            //.transforms(CenterCrop(), RoundedCorners(context.resources.getDimensionPixelOffset(R.dimen.image_corner_radius)))
            .into(this)
}