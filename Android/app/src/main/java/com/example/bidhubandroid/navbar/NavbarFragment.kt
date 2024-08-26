package com.example.bidhubandroid.navbar

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.bidhubandroid.R
import com.example.bidhubandroid.databinding.FragmentNavbarBinding

class NavbarFragment : Fragment() {
    private var _binding: FragmentNavbarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NavbarViewModel by activityViewModels();

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavbarBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.login.observe(viewLifecycleOwner) {
            binding.loginBtn.text = it
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}