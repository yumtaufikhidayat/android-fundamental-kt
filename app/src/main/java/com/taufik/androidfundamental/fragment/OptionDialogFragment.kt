package com.taufik.androidfundamental.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.taufik.androidfundamental.R
import com.taufik.androidfundamental.databinding.FragmentOptionDialogBinding

class OptionDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentOptionDialogBinding
    private var optionDialogListener: OnOptionDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOptionDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseCoach()
    }

    private fun chooseCoach() {
        binding.apply {
            btnChoose.setOnClickListener {
                val checkRadioButtonId = rgOptions.checkedRadioButtonId
                if (checkRadioButtonId != -1) {
                    val coach: String? = when (checkRadioButtonId) {
                        R.id.rbSaf -> rbSaf.text.toString().trim()
                        R.id.rbMou -> rbMou.text.toString().trim()
                        R.id.rbLvg -> rbLvg.text.toString().trim()
                        R.id.rbMoyes -> rbMoyes.text.toString().trim()
                        else -> null
                    }
                    optionDialogListener?.onOptionChosen(coach)
                    dialog?.dismiss()
                }
            }

            btnClose.setOnClickListener {
                dialog?.cancel()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragment = parentFragment
        if (fragment is DetailCategoryFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
    }

    override fun onDetach() {
        super.onDetach()

        this.optionDialogListener = null
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }
}