package com.example.bidhubandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bidhubandroid.api.data.TossRequest
import com.example.bidhubandroid.databinding.ActivityMainBinding
import com.example.bidhubandroid.databinding.ActivityPaymentBinding
import com.example.bidhubandroid.member.RechargeViewModel
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethod
import java.sql.Time
import java.time.LocalDateTime
import java.util.UUID

class PaymentActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPaymentBinding.inflate(layoutInflater)}
    private val share = UserSharedPreferences.sharedPreferences
    private val id = share.getString("id", "login")
    private val rechargeViewModel: RechargeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val clientKey = "test_gck_docs_Ovk5rk1EwkEbP0W43n07xlzm"

        // 페이먼츠 위젯에 필요한 기본 속성에 대해 세팅해준다!
        val paymentWidget = PaymentWidget(
            activity = this@PaymentActivity,
            clientKey = clientKey,
            customerKey = clientKey
        )

        // 앱 화면에 결제창에 완전히 로딩이 되면 아래의 메세지가 로그에 찍힌다. 꼭 확인 해볼것!
        val paymentMethodWidgetStatusListener = object : PaymentWidgetStatusListener {
            override fun onFail(fail: TossPaymentResult.Fail) {
            }

            override fun onLoad() {
                binding.payButton.visibility = View.VISIBLE
            }
        }

        // 페이먼츠 위젯에 결제 방법, 금액 등에 대해 설정할 부분이다.
        // 지금은 샘플 코드라 대부분의 옵션이 생략되어 있다.
        paymentWidget.run {
            val amount = intent.getLongExtra("amount", 0)
            renderPaymentMethods(
                method = binding.paymentWidget,
                amount = PaymentMethod.Rendering.Amount(amount),
                paymentWidgetStatusListener = paymentMethodWidgetStatusListener
            )

            renderAgreement(binding.agreementWidget)
        }

        // 결제버튼을 누르면 다음 단계로 넘어가게 하는 로직.
        // 이 부분은 토스가 제공하지 않으므로 스스로 xml에 버튼 추가해서 이벤트 리스너 셋팅해줘야 한다.
        val orderId = id + "_" + UUID.randomUUID()
        binding.payButton.setOnClickListener {
            paymentWidget.requestPayment(
                paymentInfo = PaymentMethod.PaymentInfo(
                    orderId = orderId,
                    orderName = "orderName"
                ),
                paymentCallback = object : PaymentCallback {
                    // 결제 프로세스에 대한 콜백 함수이다.
                    override fun onPaymentSuccess(success: TossPaymentResult.Success) {
                        var amount = success.amount.toString()
                        amount = amount.substring(0 until amount.length - 2)
                        val body = TossRequest(
                            tid = success.orderId,
                            partner_user_id = id,
                            pg_token = amount,
                            cid = null,
                            partner_order_id = null
                        )
                        rechargeViewModel.toss(body) { res ->
                            val intent = Intent()
                            intent.putExtra("result", res) // 전달할 데이터 추가
                            setResult(Activity.RESULT_OK, intent) // 결과 코드와 함께 Intent 설정
                            finish() // Activity 종료
                        }
                    }

                    override fun onPaymentFailed(fail: TossPaymentResult.Fail) {
                        Log.e("fail:::11111111", fail.errorMessage)
                    }
                }
            ) // request
        } // listener
    }
}