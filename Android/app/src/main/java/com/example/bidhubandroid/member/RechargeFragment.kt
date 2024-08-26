package com.example.bidhubandroid.member

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
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