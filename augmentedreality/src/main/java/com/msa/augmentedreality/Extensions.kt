package com.msa.augmentedreality

import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class SpectacleGlideModule : AppGlideModule()

fun ImageView.loadUrl(url: String) {
    GlideApp.with(context)
            .load(url)
            .centerCrop()
            .into(this)
}