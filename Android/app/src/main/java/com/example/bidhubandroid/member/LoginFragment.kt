package com.example.bidhubandroid.member

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bidhubandroid.R
import com.example.bidhubandroid.UserSharedPreferences
import com.example.bidhubandroid.api.data.LoginBody
import com.example.bidhubandroid.databinding.FragmentLoginBinding
import com.example.bidhubandroid.databinding.FragmentNavbarBinding
import com.example.bidhubandroid.navbar.NavbarViewModel
import com.example.bidhubandroid.setOnSingleClickListener

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels();
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signin.setOnSingleClickListener {
            val ID = binding.id.text.toString()
            val PW = binding.pw.text.toString()
            if (ID.isBlank() || PW.isBlank()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Warning!")
                    .setMessage("아이디 및 비밀번호를 입력해주세요")
                    .show()
            }else {
                val body: LoginBody = LoginBody(id = ID, pw = PW)
                viewModel.login(body, requireContext()) {
                    findNavController().navigate(R.id.action_loginFragment_to_auctionListFragment)
                }
            }
        }

        // to Main
        binding.navi.BidHub.setOnSingleClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_auctionListFragment)
        }

        // to signup
        binding.signup.setOnSingleClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}