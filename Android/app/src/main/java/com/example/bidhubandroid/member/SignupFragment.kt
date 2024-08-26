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
import com.example.bidhubandroid.api.data.SignupBody
import com.example.bidhubandroid.databinding.FragmentLoginBinding
import com.example.bidhubandroid.databinding.FragmentSignupBinding
import com.example.bidhubandroid.setOnSingleClickListener

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignupViewModel by viewModels();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // signup
        binding.signup.setOnSingleClickListener {
            val ID = binding.id.text.toString()
            val NICK = binding.nick.text.toString()
            val PW = binding.password.text.toString()
            val rPW = binding.rePassword.text.toString()
            val context = requireContext()
            if (ID.isBlank() || PW.isBlank() || rPW.isBlank()) {
                AlertDialog.Builder(context)
                    .setTitle("Warning!")
                    .setMessage("* 모양이 있는 곳은 필수 입력란입니다.")
                    .show()
            }else if (PW != rPW){
                AlertDialog.Builder(context)
                    .setTitle("Warning!")
                    .setMessage("비밀번호를 다시 확인해주세요")
                    .show()
            }else {
                val body = SignupBody(ID, NICK, PW)
                viewModel.signup(body, context) {
                    findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
                }
            }

        }

        // to main
        binding.navi.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_auctionListFragment)
        }
        // to login
        binding.signin.setOnSingleClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}