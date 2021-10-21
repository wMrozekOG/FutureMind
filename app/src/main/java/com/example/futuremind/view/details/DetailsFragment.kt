package com.example.futuremind.view.details

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import com.example.futuremind.databinding.FragmentItemDetailBinding
import com.example.futuremind.view.base.BaseFragment
import android.webkit.WebView
import android.widget.Toast
import com.example.futuremind.extensions.visible

class DetailsFragment: BaseFragment<FragmentItemDetailBinding>() {

    override val viewBinding: FragmentItemDetailBinding
        get() = FragmentItemDetailBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.run {
            val url = DetailsFragmentArgs.fromBundle(this).url

            if(url.isNotBlank()) {
                binding.webview.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        view.loadUrl(url);
                        return true
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        binding.progress.visible(true)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        binding.progress.visible(false)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        handleError()
                    }
                }
                binding.webview.loadUrl(url)
            } else {
                handleError()
            }
        } ?: run {
            handleError()
        }
    }

    private fun handleError() {
        Toast.makeText(
            requireContext(),
            "Could not load page",
            Toast.LENGTH_SHORT
        ).show()

        navigateUp()
    }
}