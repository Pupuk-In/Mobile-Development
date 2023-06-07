package com.capstone.pupukdotin.ui.history

import android.os.Bundle
import android.view.View
import com.capstone.pupukdotin.databinding.FragmentHistoryBinding
import com.capstone.pupukdotin.ui.common.BaseFragment


class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    override fun getViewBinding(): FragmentHistoryBinding = FragmentHistoryBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}