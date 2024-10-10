package com.example.bidhubandroid.member

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.UpdateRequest
import com.example.bidhubandroid.databinding.FragmentUpdateBinding
import com.example.bidhubandroid.setOnSingleClickListener

class UpdateFragment : Fragment() {

    private val viewModel: UpdateViewModel by viewModels()
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val share = UserSharedPreferences.sharedPreferences
    private val id = share.getString("id", "error")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
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

        binding.password.setOnSingleClickListener {
            val alert = AlertDialog.Builder(requireContext())
            val p1 = binding.p1.text.toString()
            val p2 = binding.p2.text.toString()
            if (p1.isBlank() || p2.isBlank()) {
                alert
                    .setTitle("알림")
                    .setMessage("비밀번호를 입력해주세요")
                    .show()
            }else if (p1 != p2) {
                alert
                    .setTitle("알림")
                    .setMessage("비밀번호가 틀렸습니다.")
                    .show()
            }else {
                val body = UpdateRequest(id = id, before = p1, after = p2)
                viewModel.updatePW(body) { res ->
                    AlertDialog.Builder(requireContext())
                        .setTitle("알림")
                        .setMessage(res.message)
                        .show()
                    if (res.status!!) {
                        binding.p1.text.clear()
                        binding.p2.text.clear()
                    }
                }
            }
        } // button1

        binding.nickname.setOnSingleClickListener {
            val nick = binding.n.text.toString()
            if (nick.isBlank()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("알림")
                    .setMessage("닉네임을 입력해주세요")
                    .show()
            }else {
                viewModel.updateNICK(UpdateRequest(id = id, before = null, after = nick)){ res ->
                    AlertDialog.Builder(requireContext())
                        .setTitle("알림")
                        .setMessage(res.message)
                        .show()
                    if (res.status!!) {
                        binding.n.text.clear()
                    }
                }
            }
        }

        binding.navi.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_auctionListFragment)
        }
        binding.navi.regist.setOnSingleClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_registFragment)
        }
        binding.navi.loginBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_mypageFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}