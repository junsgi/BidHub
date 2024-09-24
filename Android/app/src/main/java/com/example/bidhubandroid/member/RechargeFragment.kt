package com.example.bidhubandroid.member

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.KakaoPointRequest
import com.example.bidhubandroid.databinding.FragmentRechargeBinding
import com.example.bidhubandroid.setOnSingleClickListener
import java.net.URISyntaxException

class RechargeFragment : Fragment() {

    private val viewModel: RechargeViewModel by viewModels()
    private var _binding: FragmentRechargeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargeBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val share = UserSharedPreferences.sharedPreferences
        binding.navi.loginBtn.text = share.getString("nickname", "error")

        binding.toss.setOnSingleClickListener {
            val ab = TossWebView(requireContext())

        }
        // to main
        binding.navi.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_rechargeFragment_to_auctionListFragment)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class TossWebView(val context: Context):WebViewClient() {
    // API 수준 24 이상을 위한 메소드
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        val url = request.url.toString()
        return handleUrl(url)
    }
    // 공통 URL 처리 로직
    private fun handleUrl(url: String): Boolean {
        if (!URLUtil.isNetworkUrl(url) && !URLUtil.isJavaScriptUrl(url)) {
            val uri = try {
                Uri.parse(url)
            } catch (e: Exception) {
                return false
            }
            return when (uri.scheme) {
                "intent" -> {
                    startSchemeIntent(url)
                }
                else -> {
                    return try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                        true
                    } catch (e: Exception) {
                        false
                    }
                }
            }
        } else {
            return false
        }
    }
    private fun startSchemeIntent(url: String): Boolean {
        val schemeIntent: Intent = try {
            Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        } catch (e: URISyntaxException) {
            return false
        }
        try {
            context.startActivity(schemeIntent)
            return true
        } catch (e: ActivityNotFoundException) {
            val packageName = schemeIntent.getPackage()
            if (!packageName.isNullOrBlank()) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packageName")
                    )
                )
                return true
            }
        }
        return false
    }

}