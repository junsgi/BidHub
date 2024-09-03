package com.example.bidhubandroid.auction

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.databinding.FragmentAuctionListBinding
import com.example.bidhubandroid.setOnSingleClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AuctionListFragment : Fragment() {
    private var _binding: FragmentAuctionListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuctionListViewModel by viewModels()
    private val myAdapter = AuctionItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuctionListBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.include.loginBtn.text =
            UserSharedPreferences.sharedPreferences.getString("nickname", "login")
        binding.include.BidHub.setOnSingleClickListener {
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle("BidHub!")
            alert.setMessage("BidHub!")
            alert.setNegativeButton("확인") { dialog, which ->
                UserSharedPreferences.sharedPreferences.edit().clear().apply()
                dialog.dismiss() // 대화상자 닫기
            }
            alert.show()
//            binding.include.loginBtn.text = if (binding.include.loginBtn.text.toString() == "로그인") "뭐 d련아" else "로그인"
        }
        Log.d("errorJun", myAdapter.toString())
        binding.auctionList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter
        }

//        lifecycleScope.launch {
//            viewModel.items.collectLatest {
//                myAdapter.submitData(it)
//            }
//        }

        // refresh layout
        binding.listLayout.setOnRefreshListener {
            myAdapter.refresh()
            binding.listLayout.isRefreshing = false
        }
        // refresh layout end

        // to Login
        binding.include.loginBox.setOnSingleClickListener {
            if (binding.include.loginBtn.text.toString() != "login" && binding.include.loginBtn.text.toString() != "error" ) {
                findNavController().navigate(R.id.action_auctionListFragment_to_mypageFragment)
            } else {
                findNavController().navigate(R.id.action_auctionListFragment_to_loginFragment)
            }
        }
        // Login end
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
