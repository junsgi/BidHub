package com.example.bidhubandroid.auction

import android.app.AlertDialog
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
import com.example.bidhubandroid.api.data.BiddingRequest
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
    private val share = UserSharedPreferences.sharedPreferences
    private val nav = share.getString("nickname", "login")
    private val user = share.getString("id", "login")
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
        if (!nav.equals("login")) {
            binding.include2.regist.visibility = View.VISIBLE
            binding.include2.regist.setOnSingleClickListener {
                findNavController().navigate(R.id.action_detailFragment_to_registFragment)
            }
        }

        // Rest API
        this.getDetail();

        // refresh layout
        binding.detailLayout.setOnRefreshListener {
            this.getDetail();
            binding.detailLayout.isRefreshing = false

        }
        // refresh layout end
        binding.button1.setOnSingleClickListener {
            when (binding.button1.text.toString()) {
                "입찰하기" -> bidding()
                "경매 취소" -> close()
                "경매 삭제" -> remove()
            }
        }
        if (binding.button2.visibility == View.VISIBLE) {
            binding.button2.setOnSingleClickListener {
                bidding_imm()
            }
        }

        // to main
        binding.include2.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_auctionListFragment)
        }

        // login or mypage
        binding.include2.loginBtn.setOnSingleClickListener {
            when (nav) {
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
            if (user == "login") {
                binding.button1.text = "로그인 후 이용해주세요"
                binding.button2.visibility = View.GONE
                binding.button1.setOnSingleClickListener {
                    findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
                }
            } else if (!res.memId.equals(user)) {
                binding.button1.text = "입찰하기"
                if (res.aitemImmediate != null) {
                    binding.button2.text = "즉시 구매"
                } else {
                    binding.button2.visibility = View.GONE
                }
            } else {
                binding.button2.visibility = View.GONE
                binding.button1.text = if (res.status.equals("1")) "경매 취소" else "경매 삭제"
            }

        }// onSuccess
    }

    private fun bidding() {
        var current = binding.current.text.toString()
        current = current.substring(0, current.length - 1)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("입찰")
            .setMessage("입찰하시겠습니까?\n환불 x, 확인 후 취소 x")
            .setPositiveButton("확인") { dialog, which ->
                // 확인 버튼 클릭 시 동작
                viewModel.bidding(BiddingRequest(userId = user, itemId = aid, current = current, null)) { res ->
                    AlertDialog.Builder(requireContext())
                        .setTitle("알림")
                        .setMessage(res.message)
                        .show()
                    this.getDetail()
                }
            }
            .setNegativeButton("취소") { dialog, which ->
                // 취소 버튼 클릭 시 동작
            }
            .show()
    }
    private fun bidding_imm() {
        var current = binding.current.text.toString()
        current = current.substring(0, current.length - 1)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("즉시 구매")
            .setMessage("즉시 구매하시겠습니까?\n환불 x, 확인 후 취소 x")
            .setPositiveButton("확인") { dialog, which ->
                // 확인 버튼 클릭 시 동작
                viewModel.bidding_imm(BiddingRequest(userId = user, itemId = aid, current = current, null)) { res ->
                    AlertDialog.Builder(requireContext())
                        .setTitle("알림")
                        .setMessage(res.message)
                        .show()
                    this.getDetail()
                }
            }
            .setNegativeButton("취소") { dialog, which ->
                // 취소 버튼 클릭 시 동작
            }
            .show()
    }

    private fun close() {
        var current = binding.current.text.toString()
        current = current.substring(0, current.length - 1)
        viewModel.close(BiddingRequest(user, aid, current, null)) { res ->
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("취소")
                .setMessage(res.message!!)
                .setPositiveButton("확인") { dialog, which ->
                    // 확인 버튼 클릭 시 동작
                    viewModel.decide(BiddingRequest(userId = user, itemId = aid, current = current, true)) { res ->
                        AlertDialog.Builder(requireContext())
                            .setTitle("알림")
                            .setMessage(res.message!!)
                            .show()
                        this.getDetail()
                    }
                }
                .setNegativeButton("취소") { dialog, which ->
                    // 취소 버튼 클릭 시 동작
                    viewModel.decide(BiddingRequest(userId = user, itemId = aid, current = current, false)) { res ->
                        AlertDialog.Builder(requireContext())
                            .setTitle("알림")
                            .setMessage(res.message!!)
                            .show()
                        this.getDetail()
                    }
                }
                .show()
        }

    }

    private fun remove() {
        var current = binding.current.text.toString()
        current = current.substring(0, current.length - 1)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("삭제")
            .setMessage("삭제하시겠습니까?")
            .setPositiveButton("확인") { dialog, which ->
                // 확인 버튼 클릭 시 동작
                viewModel.remove(BiddingRequest(user, aid, current, null)) { res ->
                    AlertDialog.Builder(requireContext())
                        .setTitle("알림")
                        .setMessage(res.message)
                        .show()
                    if (res.status!!) {
                        findNavController().navigate(R.id.action_detailFragment_to_auctionListFragment)
                    }
                }
            }
            .setNegativeButton("취소") { dialog, which ->
                // 취소 버튼 클릭 시 동작
            }
            .show()
    }
}