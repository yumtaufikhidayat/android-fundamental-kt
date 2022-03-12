package com.taufik.androidfundamental.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.taufik.androidfundamental.databinding.FragmentNavCategoryBinding

class NavCategoryFragment : Fragment() {

    private var _binding: FragmentNavCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNavCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAction()
    }

    private fun setAction() {
        binding.apply {

            // send data using safeargs
            btnCategoryLifeStyle.setOnClickListener {
                val toDetailCategoryFragment = NavCategoryFragmentDirections
                    .actionNavCategoryFragmentToNavDetailCategoryFragment()
                    .apply {
                        name = "LifeStyle"
                        stocks = 7
                    }
                it.findNavController().navigate(toDetailCategoryFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

