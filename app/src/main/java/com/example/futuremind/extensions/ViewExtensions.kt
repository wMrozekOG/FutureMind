package com.example.futuremind.extensions

import android.view.View

/**
 * Sets the visibility of view
 *
 * @param isVisible
 */
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}
