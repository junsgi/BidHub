package com.example.bidhubandroid.auction

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.bidhubandroid.R
import com.example.bidhubandroid.api.data.DetailResponse
import com.example.bidhubandroid.databinding.FragmentDetailBinding
import com.example.bidhubandroid.databinding.FragmentRechargeBinding
import com.example.bidhubandroid.member.RechargeViewModel

class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetailFragmentArgs by navArgs()
        val aid:String = args.aitemId
        Log.d("aidaidaid", aid)
        viewModel.getDetail(aid) { res:DetailResponse ->
//            binding.title.text = res.aitemTitle
//            binding.memId.text = res.memId
//            binding.current.text = res.aitemContent.plus("원")
//            binding.start.text = res.aitemContent.plus("원")
        }
        // refresh layout
        binding.detailLayout.setOnRefreshListener {
            binding.detailLayout.isRefreshing = false
        }
        // refresh layout end
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}