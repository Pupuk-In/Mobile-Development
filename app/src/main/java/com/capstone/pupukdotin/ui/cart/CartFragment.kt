package com.capstone.pupukdotin.ui.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.databinding.FragmentCartBinding
import com.capstone.pupukdotin.ui.adapter.TesCartAdapter
import com.capstone.pupukdotin.ui.checkout.CheckOutActivity
import com.capstone.pupukdotin.ui.common.BaseFragment

class CartFragment : BaseFragment<FragmentCartBinding>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TesCartAdapter
    override fun getViewBinding(): FragmentCartBinding = FragmentCartBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.CartRecyclerView)
        adapter = TesCartAdapter()

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        onSetUpAction()

    }

    private fun onSetUpAction() {
        binding.apply {
            cartButtonPesan.setOnClickListener {
                val intent = Intent(requireActivity(), CheckOutActivity::class.java)
                startActivity(intent)
            }
        }
    }
}