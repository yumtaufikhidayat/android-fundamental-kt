package com.taufik.androidfundamental.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDetailCategoryFragment()
    }

    private fun setDetailCategoryFragment() = with(binding) {
        btnDetailCategory.setOnClickListener {
            val mDetailCategoryFragment = DetailCategoryFragment()
            val mBundle = Bundle()
            mBundle.putString(DetailCategoryFragment.EXTRA_NAME, "Lifestyle")
            val description = "Kategori ini akan berisi produk-produk lifestyle"
            mDetailCategoryFragment.arguments = mBundle
            mDetailCategoryFragment.description = description

            val mFragmentManager = parentFragmentManager
            mFragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.frameContainer,
                    mDetailCategoryFragment,
                    DetailCategoryFragment::class.java.simpleName
                )
            }
        }
    }
}