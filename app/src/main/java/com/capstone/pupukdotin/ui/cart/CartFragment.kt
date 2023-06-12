package com.capstone.pupukdotin.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pupukdotin.R
import com.capstone.pupukdotin.data.remote.network.NetworkResult
import com.capstone.pupukdotin.data.remote.response.carts.CartItemsResponse
import com.capstone.pupukdotin.databinding.FragmentCartBinding
import com.capstone.pupukdotin.ui.ViewModelFactory
import com.capstone.pupukdotin.ui.adapter.TesCartAdapter
import com.capstone.pupukdotin.ui.checkout.CheckOutActivity
import com.capstone.pupukdotin.ui.common.BaseFragment

class CartFragment : BaseFragment<FragmentCartBinding>(), TesCartAdapter.OnItemChecked {

    private val viewModel by viewModels<CartViewModel> { ViewModelFactory(requireActivity()) }
    override fun getViewBinding(): FragmentCartBinding = FragmentCartBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCartItems()
        onSetUpAction()
        setupAdapter()
        setupViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.editCart()
    }



    private fun setupViewModel() {
        viewModel.cartItem.observe(viewLifecycleOwner) { result ->
            when(result) {
                is NetworkResult.Loading -> {
                    showLoading(true)
                }

                is NetworkResult.Success -> {
                    showLoading(false)
                    setupSuccessView(result.data.cart)
                }

                is NetworkResult.Error -> {
                    showLoading(false)
                }
            }
        }

        viewModel.editCartMessage.observe(viewLifecycleOwner) {result ->
            when(result) {
                is NetworkResult.Loading -> {
                    // Do Nothing
                }

                is NetworkResult.Success -> {
                    // Do Nothing
                }

                is NetworkResult.Error -> {
                    showToast(result.error.toString())
                }
            }
        }

        viewModel.listItem.observe(viewLifecycleOwner) { list->
            binding.constraintLayout.isVisible = list.isNotEmpty()
            binding.cartRecyclerView.adapter = TesCartAdapter(list, this)
        }

        viewModel.totalPrice.observe(viewLifecycleOwner) { price ->
            binding.totalHarga.text = getString(R.string.price_format, price)
        }
    }

    private fun setupSuccessView(cart: CartItemsResponse.Cart?) {
        viewModel.addAll(cart?.cartItem ?: emptyList())
    }

    private fun showLoading(value: Boolean) {
        binding.pbLoadingScreen.isVisible = value
        binding.nsvContent.isVisible = !value
    }

    private fun setupAdapter() {
        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TesCartAdapter(emptyList())
        }
    }

    private fun onSetUpAction() {
        binding.apply {
            cartButtonPesan.setOnClickListener {
                val intent = Intent(requireActivity(), CheckOutActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onItemDelete(itemId: Int) {
//        TODO("Not yet implemented")
    }

    override fun addQuantity(position: Int) {
        viewModel.addQuantity(position)
    }

    override fun subtractQuantity(position: Int) {
        viewModel.subtractQuantity(position)
    }
}