package com.example.bidhubandroid.navbar

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bidhubandroid.R
import com.example.bidhubandroid.databinding.FragmentNavbarBinding

class NavbarFragment : Fragment() {
    private var _binding:FragmentNavbarBinding? = null
    private val binding get()= _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavbarBinding.inflate(inflater, container, false)
        return _binding!!.root
    }
}