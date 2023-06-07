package com.capstone.pupukdotin.ui.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.FragmentStoreProfileBinding
import com.capstone.pupukdotin.ui.common.BaseFragment

class StoreProfileFragment : BaseFragment<FragmentStoreProfileBinding>() {
    override fun getViewBinding(): FragmentStoreProfileBinding = FragmentStoreProfileBinding.inflate(layoutInflater)
}