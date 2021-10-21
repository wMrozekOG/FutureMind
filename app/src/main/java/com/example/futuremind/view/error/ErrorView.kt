package com.example.futuremind.view.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import com.example.futuremind.extensions.visible
import com.example.futuremind.R
import com.example.futuremind.databinding.ErrorViewBinding
import com.google.android.material.snackbar.Snackbar

class ErrorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
: FrameLayout(context, attrs, defStyleAttr) {

    private var binding: ErrorViewBinding =
        ErrorViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setErrorState(errorState: ErrorState) {
        when (errorState) {
            ErrorState.NETWORK_ERROR -> {
                showFullScreen(ErrorConfiguration.NETWORK)
            }
            ErrorState.DATA_ERROR -> {
                showFullScreen(ErrorConfiguration.BROKEN_DATA)
            }
            ErrorState.NO_ERROR -> {
                hide()
            }
        }
    }

    private fun showFullScreen(errorConfiguration: ErrorConfiguration) {
        visible(true)
        binding.errorIcon.setImageDrawable(ResourcesCompat.getDrawable(resources, errorConfiguration.imageResourceId, null))
        binding.errorTitle.text = resources.getString(errorConfiguration.titleResourceId)
        binding.errorMessage.text = resources.getString(errorConfiguration.descriptionResourceId)
    }

    private fun hide() {
        visible(false)
    }

    fun showSnackBar(errorConfiguration: ErrorConfiguration, action: () -> Unit) {
        val message = resources.getString(errorConfiguration.titleResourceId) + " " +
                resources.getString(errorConfiguration.descriptionResourceId)

        val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        snackBar.setAction(resources.getString(R.string.try_again)) { action() }
        snackBar.show()
    }
}