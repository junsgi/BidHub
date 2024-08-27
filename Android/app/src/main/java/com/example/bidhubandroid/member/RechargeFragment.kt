package com.example.bidhubandroid.member

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.PayActivity
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.KakaoPointRequest
import com.example.bidhubandroid.databinding.FragmentRechargeBinding
import com.example.bidhubandroid.setOnSingleClickListener

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


        binding.kakaopay.setOnSingleClickListener {
            val point = binding.point.text
            if (point.isEmpty() || point.isBlank() || point.toString().toInt() <= 0) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Warning!")
                    .setMessage("금액을 입력해주세요!")
                    .show()
            } else {
                val body = KakaoPointRequest(
                    cid = null,
                    partner_order_id = null,
                    item_name = null,
                    quantity = null,
                    tax_free_amount = null,
                    approval_url = null,
                    cancel_url = null,
                    fail_url = null,
                    partner_user_id = share.getString("id", "error").toString(),
                    total_amount = point.toString().toInt()
                )
                viewModel.kakaopay(body) { s ->
                    binding.web.settings.javaScriptEnabled = true
                    binding.rechargelayout.visibility = View.INVISIBLE
                    binding.web.webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            // 페이지 로딩 완료 후 처리
                            Log.d("석섹스1", url!!.split("=")[1])
//                            binding.web.loadUrl(url.split("=")[1])
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            // 에러 발생 시 처리
                            Log.d("URLERROR", request?.url.toString())
                            val url = request?.url.toString()
                            val payActivity:PayActivity = PayActivity(url)
                            val intent: Intent = Intent(requireContext(), payActivity.javaClass)
//                            startActivity(intent)
                        }
                    }
                    binding.web.loadUrl(s)
                }
            }
        }


        // to main
        binding.navi.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_rechargeFragment_to_auctionListFragment)
        }
    }
    fun startKakaoPay(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // 카카오톡 앱이 설치되어 있지 않음
            val fallbackUrl = intent.getStringExtra("S.browser_fallback_url")
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl))
            startActivity(browserIntent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}