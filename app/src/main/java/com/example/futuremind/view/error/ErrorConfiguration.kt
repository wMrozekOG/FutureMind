package com.example.futuremind.view.error

import androidx.annotation.Keep
import com.example.futuremind.R

@Keep
enum class ErrorConfiguration(val titleResourceId: Int, val descriptionResourceId: Int, val imageResourceId: Int) {
    NETWORK(
        titleResourceId = R.string.offline_error_title,
        descriptionResourceId = R.string.offline_error_message,
        imageResourceId = R.drawable.ic_pic_error_offline
    ),
    BROKEN_DATA(
        titleResourceId = R.string.load_data_error_title,
        descriptionResourceId = R.string.load_data_error_message,
        imageResourceId = R.drawable.ic_pic_error_not_loaded
    )
}