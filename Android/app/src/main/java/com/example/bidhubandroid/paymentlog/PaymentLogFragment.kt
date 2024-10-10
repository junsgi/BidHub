package com.example.bidhubandroid.paymentlog

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.databinding.FragmentPaymentLogBinding
import com.example.bidhubandroid.setOnSingleClickListener

class PaymentLogFragment : Fragment() {

    private val viewModel: PaymentLogViewModel by viewModels()
    private var _binding: FragmentPaymentLogBinding? = null
    private val binding get() = _binding!!
    private val adapter = PaymentLogAdapter()
    private val share = UserSharedPreferences.sharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentLogBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = share.getString("nickname", "login")
        binding.navi.loginBtn.text = nav
        if (!nav.equals("login")) {
            binding.navi.regist.visibility = View.VISIBLE
            binding.navi.regist.setOnSingleClickListener {
                findNavController().navigate(R.id.action_paymentLogFragment_to_registFragment)
            }
        }

        val manager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter
        val userId = share.getString("id", "login")!!
        viewModel.getLog(userId) { list ->
            adapter.setData(list)
        }

        binding.navi.loginBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.action_paymentLogFragment_to_mypageFragment)
        }
        binding.navi.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_paymentLogFragment_to_auctionListFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}