package com.kmozcan1.docebotest.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kmozcan1.docebotest.databinding.SortBottomSheetBinding

/**
 * Created by Kadir Mert Özcan on 04-Jan-21.
 */
class SortBottomSheetFragment: BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = SortBottomSheetFragment()
    }

    lateinit var binding: SortBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SortBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    private fun setUpViews() {

    }
}