package com.taufik.androidfundamental.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.FragmentNavDetailCategoryBinding

class NavDetailCategoryFragment : Fragment() {

    private var _binding: FragmentNavDetailCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNavDetailCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBundleData()
    }

    private fun setBundleData() {
        val dataName = NavDetailCategoryFragmentArgs.fromBundle(arguments as Bundle).name
        val description = NavDetailCategoryFragmentArgs.fromBundle(arguments as Bundle).stocks

        binding.apply {
            tvDetailCategory.text = dataName
            tvCategoryDescription.text = String.format("%s: %s", getString(R.string.tvStock), description)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}