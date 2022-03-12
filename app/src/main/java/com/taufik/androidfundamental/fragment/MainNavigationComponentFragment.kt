package com.taufik.androidfundamental.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.FragmentMainNavigationComponentBinding

class MainNavigationComponentFragment : Fragment() {

    private var _binding: FragmentMainNavigationComponentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainNavigationComponentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAction()
    }

    private fun setAction() {
        binding.apply {
            btnCategory.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainNavigationComponentFragment_to_navCategoryFragment)
            }

            btnProfile.setOnClickListener {
                it.findNavController().navigate(R.id.action_mainNavigationComponentFragment_to_profileActivity)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}