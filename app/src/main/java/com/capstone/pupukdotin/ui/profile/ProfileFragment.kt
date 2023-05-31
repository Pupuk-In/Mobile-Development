package com.capstone.pupukdotin.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.pupukdotin.databinding.FragmentProfileBinding
import com.capstone.pupukdotin.ui.store.StoreActivity

class ProfileFragment : Fragment() {


    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLihatProfil.setOnClickListener {
            val intent = Intent(requireActivity(), LihatProfileActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLihatToko.setOnClickListener {
            val intent = Intent(requireActivity(), StoreActivity::class.java)
            startActivity(intent)
        }

    }

}