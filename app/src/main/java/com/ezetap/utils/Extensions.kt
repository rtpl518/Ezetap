package com.ezetap.utils

import android.content.Context
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

fun View.visible() {
    isVisible = true
}

fun View.gone() {
    isVisible = false
}

fun View.invisible() {
    isInvisible = true
}

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun Context.pxToDp(px: Int): Int {
    return (px / resources.displayMetrics.density).toInt()
}