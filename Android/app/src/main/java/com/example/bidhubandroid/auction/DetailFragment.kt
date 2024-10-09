package com.example.bidhubandroid.auction

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.DetailResponse
import com.example.bidhubandroid.databinding.FragmentDetailBinding
import com.example.bidhubandroid.databinding.FragmentRechargeBinding
import com.example.bidhubandroid.member.RechargeViewModel
import com.example.bidhubandroid.setOnSingleClickListener

class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val aid get() = args.aitemId
    private val nav = UserSharedPreferences.sharedPreferences.getString("nickname", "login")
    private val user = UserSharedPreferences.sharedPreferences.getString("id", "login")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.include2.loginBtn.text = nav

        // Rest API
        this.getDetail();

        // refresh layout
        binding.detailLayout.setOnRefreshListener {
            this.getDetail();
            binding.detailLayout.isRefreshing = false

        }
        // refresh layout end

        // to main
        binding.include2.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_auctionListFragment)
        }

        // login or mypage
        binding.include2.loginBtn.setOnSingleClickListener {
            when(nav) {
                "login" -> findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
                else -> findNavController().navigate(R.id.action_detailFragment_to_mypageFragment)
            }

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getDetail() {
        viewModel.getDetail(aid) { res ->
            binding.title.text = res.aitemTitle
            Glide.with(binding.imgView.context)
                .load("http://172.30.1.25:3977/auctionitem/img/${aid}")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.imgView)
            binding.memId.text = res.memId
            binding.remaining.text = AuctionItemAdapter.convertSeconds(res.aitemDate);
            binding.current.text = res.aitemCurrent.toString().plus("원")
            binding.start.text = res.aitemStart.plus("원")
            binding.bid.text = res.aitemBid.plus("원")
            if (res.aitemImmediate == null) {
                binding.imm.visibility = View.GONE
                binding.immediate.visibility = View.GONE
            } else {
                binding.immediate.text = res.aitemImmediate.plus("원")
            }
            binding.content.text = res.aitemContent ?: "상품 설명 없음"

            // button logic
            if (user == "login"){
                binding.button1.text = "로그인 후 이용해주세요"
                binding.button2.visibility = View.GONE
                binding.button1.setOnSingleClickListener {
                    findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
                }
            }else if (!res.memId.equals(user)){
                binding.button1.text = "입찰하기"
                if (res.aitemImmediate != null) {
                    binding.button2.text = "즉시 구매"
                }else {
                    binding.button2.visibility = View.GONE
                }
            }else{
                binding.button2.visibility = View.GONE
                binding.button1.text = if (res.status.equals("1")) "경매 취소" else "경매 삭제"
            }

        }// onSuccess
    }
}