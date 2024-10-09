package com.example.bidhubandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bidhubandroid.databinding.ActivityMainBinding
import com.example.bidhubandroid.databinding.ActivityPaymentBinding
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethod
import java.sql.Time
import java.time.LocalDateTime

class PaymentActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPaymentBinding.inflate(layoutInflater)}
    private val share = UserSharedPreferences.sharedPreferences
    private val id = share.getString("id", "login")
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
                val message = "결제위젯 렌더링 완료"
                Log.d("PaymentWidgetStatusListener", message)
            }
        }

        // 페이먼츠 위젯에 결제 방법, 금액 등에 대해 설정할 부분이다.
        // 지금은 샘플 코드라 대부분의 옵션이 생략되어 있다.
        paymentWidget.run {
            renderPaymentMethods(
                method = binding.paymentWidget,
                amount = PaymentMethod.Rendering.Amount(10000),
                paymentWidgetStatusListener = paymentMethodWidgetStatusListener
            )

            renderAgreement(binding.agreementWidget)
        }

        // 결제버튼을 누르면 다음 단계로 넘어가게 하는 로직.
        // 이 부분은 토스가 제공하지 않으므로 스스로 xml에 버튼 추가해서 이벤트 리스너 셋팅해줘야 한다.
        binding.payButton.setOnClickListener {
            paymentWidget.requestPayment(
                paymentInfo = PaymentMethod.PaymentInfo(
                    orderId = id + LocalDateTime.now(),
                    orderName = "orderName"
                ),
                paymentCallback = object : PaymentCallback {
                    // 결제 프로세스에 대한 콜백 함수이다.
                    override fun onPaymentSuccess(success: TossPaymentResult.Success) {
                        Log.i("success:::", success.paymentKey)
                        Log.i("success:::", success.orderId)
                        Log.i("success:::", success.amount.toString())
                    }

                    override fun onPaymentFailed(fail: TossPaymentResult.Fail) {
                        Log.e("fail:::", fail.errorMessage)
                    }
                }
            )
        }
    }
}