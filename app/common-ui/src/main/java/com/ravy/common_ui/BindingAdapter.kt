package com.ravy.common_ui

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadImage(view: ImageView, url: String?) {
        Glide.with(view)
            .load(url)
            .circleCrop()
            .into(view)
            .clearOnDetach()
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, isVisible: Boolean?) {
        isVisible?.let { view.isVisible = it }
    }
}