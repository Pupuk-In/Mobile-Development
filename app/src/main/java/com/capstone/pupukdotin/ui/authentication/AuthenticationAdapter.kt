package com.capstone.pupukdotin.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AuthenticationAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RegisterFragment.newInstance()
            1 -> fragment = LoginFragment.newInstance()
        }

        return fragment as Fragment
    }

    interface MoveFragment {
        fun moveFragment(destination: Int)
    }

}