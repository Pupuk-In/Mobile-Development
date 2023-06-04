package com.capstone.pupukdotin.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB: ViewBinding>: Fragment() {

    abstract fun getViewBinding(): VB

    private var _binding: VB? = null

    protected val binding: VB
        get() {
            if (_binding == null) _binding = getViewBinding()
            return _binding as VB
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}