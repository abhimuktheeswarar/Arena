package com.abhi.spectacle.utilities

import android.content.res.Resources
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

@GlideModule
class SpectacleGlideModule : AppGlideModule()

fun ImageView.loadUrl(url: String) {
    GlideApp.with(context)
            .load(url)
            .centerCrop()
            .into(this)
}


fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}


fun Fragment.toast(message: CharSequence) = activity?.toast(message)

fun Fragment.longToast(message: Int) = activity?.longToast(message)

fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)