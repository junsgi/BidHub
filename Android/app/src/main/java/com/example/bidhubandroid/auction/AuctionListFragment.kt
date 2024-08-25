package com.example.bidhubandroid.auction

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bidhubandroid.R
import com.example.bidhubandroid.databinding.FragmentAuctionListBinding
import com.example.bidhubandroid.setOnSingleClickListener

class AuctionListFragment : Fragment() {
    private var _binding: FragmentAuctionListBinding? = null
    private val binding get()= _binding!!
    private val viewModel: AuctionListViewModel by viewModels()
    private val adapter = AuctionItemAdapter()
    private var size = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuctionListBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.include.BidHub.setOnSingleClickListener {
            binding.include.loginBtn.text = if (binding.include.loginBtn.text.toString() == "로그인") "뭐 d련아" else "로그인"
        }
        val manager = LinearLayoutManager(requireContext())
        binding.auctionList.layoutManager = manager
        binding.auctionList.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.setData(it.list)
            size = it.len
        }
        viewModel.getItems(1, 0, null);

        // refresh layout
        binding.listLayout.setOnRefreshListener {
            viewModel.getItems(1, 0, null)

            binding.listLayout.isRefreshing = false
        }
        // refresh layout end

        // to Login
        binding.include.loginBox.setOnSingleClickListener {
            findNavController().navigate(R.id.action_auctionListFragment_to_loginFragment)
        }
        // Login end
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}