package com.example.bidhubandroid.member

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.PaymentActivity
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.databinding.FragmentRechargeBinding
import com.example.bidhubandroid.setOnSingleClickListener
import com.tosspayments.paymentsdk.PaymentWidget
import com.tosspayments.paymentsdk.TossPayments
import com.tosspayments.paymentsdk.model.PaymentCallback
import com.tosspayments.paymentsdk.model.PaymentWidgetStatusListener
import com.tosspayments.paymentsdk.model.TossPaymentResult
import com.tosspayments.paymentsdk.view.PaymentMethod
class RechargeFragment : Fragment() {

    private val viewModel: RechargeViewModel by viewModels()
    private var _binding: FragmentRechargeBinding? = null
    private val binding get() = _binding!!
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val res = result.data?.getBooleanExtra("result", false)
            if (!(res!!)) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Warning!")
                    .setMessage("다시 시도해주세요")
                    .show()
            }else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Success!")
                    .setMessage("결제 성공!")
                    .show()
            }
            findNavController().navigate(R.id.action_rechargeFragment_to_mypageFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRechargeBinding.inflate(inflater, container, false)
        return _binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nick = UserSharedPreferences.sharedPreferences.getString("nickname", "login")
        binding.navi.loginBtn.text = nick
        if (!nick.equals("login")) {
            binding.navi.regist.visibility = View.VISIBLE
            binding.navi.regist.setOnSingleClickListener {
                findNavController().navigate(R.id.action_auctionListFragment_to_registFragment)
            }
        }

        binding.toss.setOnSingleClickListener {
            val amount = binding.point.text.toString()
            if (amount.isBlank() || amount.isEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Warning!")
                    .setMessage("금액을 입력해주세요!")
                    .show()
            }else {
                val intent = Intent(requireContext(), PaymentActivity::class.java)
                intent.putExtra("amount", amount.toLong())
                resultLauncher.launch(intent)
            }
        }

        binding.navi.BidHub.setOnSingleClickListener {
             findNavController().navigate(R.id.action_rechargeFragment_to_auctionListFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
