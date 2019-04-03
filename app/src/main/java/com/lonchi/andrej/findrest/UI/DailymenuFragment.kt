package com.lonchi.andrej.findrest.UI


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lonchi.andrej.findrest.R


class DailymenuFragment : Fragment() {

    //  TODO    ->  store and show daily menus (this is only longtime menu from url)

    //  View of this layout
    private lateinit var viewOfLayout: View

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_dailymenu, container, false)

        val menuUrl = arguments?.getString("menu_url")

        val mWebView = viewOfLayout.findViewById<WebView>(R.id.webviewDailymenu)
        mWebView.settings.javaScriptEnabled = true
        mWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        mWebView!!.loadUrl(menuUrl)

        return viewOfLayout
    }


}
