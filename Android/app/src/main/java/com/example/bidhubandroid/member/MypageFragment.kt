package com.example.bidhubandroid.member

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.databinding.FragmentMypageBinding
import com.example.bidhubandroid.setOnSingleClickListener

class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MypageViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val share = UserSharedPreferences.sharedPreferences

        viewModel.detail(share.getString("id", "error")!!) {
            binding.navi.loginBtn.text = share.getString("nickname", "error")
            binding.myId.setText("${share.getString("id", "error").toString()} (id)")
            binding.myNick.setText("${share.getString("nickname", "error").toString()} (nickname)")
            binding.myPoint.setText("${share.getLong("point", -1)} (point)")
        }

        // logout
        binding.logout.setOnSingleClickListener {
            UserSharedPreferences.sharedPreferences.edit().clear().apply()
            findNavController().navigate(R.id.action_mypageFragment_to_auctionListFragment)
        }

        // to main
        binding.navi.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_auctionListFragment)
        }

        // to recharge
        binding.recharge.setOnSingleClickListener {
            findNavController().navigate(R.id.action_mypageFragment_to_rechargeFragment)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}