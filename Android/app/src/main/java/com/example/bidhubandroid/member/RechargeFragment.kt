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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.MainActivity
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.KakaoPointRequest
import com.example.bidhubandroid.databinding.FragmentRechargeBinding
import com.example.bidhubandroid.setOnSingleClickListener
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethod
import java.net.URISyntaxException

class RechargeFragment : Fragment() {

    private val viewModel: RechargeViewModel by viewModels()
    private var _binding: FragmentRechargeBinding? = null
    private val binding get() = _binding!!
    private val test_key = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargeBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val share = UserSharedPreferences.sharedPreferences
        binding.navi.loginBtn.text = share.getString("nickname", "error")


        val paymentWidget = PaymentWidget(
            activity = MainActivity(),
            clientKey = test_key,
            customerKey = test_key,
        )

        // 앱 화면에 결제창에 완전히 로딩이 되면 아래의 메세지가 로그에 찍힌다. 꼭 확인 해볼것!
        val paymentMethodWidgetStatusListener = object : PaymentWidgetStatusListener {

            override fun onLoad() {
                val message = "결제위젯 렌더링 완료"
                Log.d("PaymentWidgetStatusListener", message)
            }

            override fun onFail(fail: TossPaymentResult.Fail) {
                val message = "결제위젯 렌더링 실"
                Log.d("PaymentWidgetStatusListener", message)
            }

        }
//        https://1-hee.tistory.com/m/85
        // 결제버튼을 누르면 다음 단계로 넘어가게 하는 로직.
        // 이 부분은 토스가 제공하지 않으므로 스스로 xml에 버튼 추가해서 이벤트 리스너 셋팅해줘야 한다.
        binding.payButton.setOnSingleClickListener {
            Log.d("ㅋㄹ릭", "ㅋㅋㅋㅋㅋㅋㅋㅋㅋ")
            paymentWidget.requestPayment(
                paymentInfo = PaymentMethod.PaymentInfo(
                                orderId = "wBWO9RJXO0UYqJMV4er8J",
                                orderName = "orderName"
                ), paymentCallback = object : PaymentCallback {
                // 결제 프로세스에 대한 콜백 함수이다.
                override fun onPaymentSuccess(success: TossPaymentResult.Success) {
                    Log.i("success:::", success.paymentKey)
                    Log.i("success:::", success.orderId)
                    Log.i("success:::", success.amount.toString())
                }

                override fun onPaymentFailed(fail: TossPaymentResult.Fail) {
                    Log.e("fail:::", fail.errorMessage)
                }
            })
        }

        binding.toss.setOnSingleClickListener {
            binding.rechargelayout.visibility = View.GONE
            binding.paymentView.visibility = View.VISIBLE
            // 페이먼츠 위젯에 결제 방법, 금액 등에 대해 설정할 부분이다.
            // 지금은 샘플 코드라 대부분의 옵션이 생략되어 있다.
            paymentWidget.run {
                val value = binding.point.text.toString().toLong()
                renderPaymentMethods(
                    method = binding.paymentWidget,
                    amount = PaymentMethod.Rendering.Amount(value),
                    paymentWidgetStatusListener = paymentMethodWidgetStatusListener
                )
                renderAgreement(binding.agreementWidget)
            }
        }

        // to main
        binding.navi.BidHub.setOnSingleClickListener {
            binding.rechargelayout.visibility = View.VISIBLE
            binding.paymentView.visibility = View.GONE
//            findNavController().navigate(R.id.action_rechargeFragment_to_auctionListFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class TossWebViewClient(val context: Context) : WebViewClient() {
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
                        Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")
                    )
                )
                return true
            }
        }
        return false
    }

}