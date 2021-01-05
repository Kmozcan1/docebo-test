package com.kmozcan1.docebotest.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kmozcan1.docebotest.presentation.ArgConstants.USER_NAME_ARG
import com.kmozcan1.docebotest.presentation.ui.ProfileFragment
import com.kmozcan1.docebotest.presentation.ui.RepositoriesFragment
import com.kmozcan1.docebotest.presentation.ui.UserViewPagerFragment
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
class UserViewPagerAdapter @Inject constructor(
    userViewPagerFragment: UserViewPagerFragment,
    private val userName: String
) : FragmentStateAdapter(userViewPagerFragment) {

    // Number of tabs
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            // First tab
            val fragment = ProfileFragment()
            fragment.arguments = Bundle().apply {
                putString(USER_NAME_ARG, userName)
            }
            return fragment
        } else if (position == 1) {
            // Second tab
            val fragment = RepositoriesFragment()
            fragment.arguments = Bundle().apply {
                putString(USER_NAME_ARG, userName)
            }
            return fragment
        }
        return ProfileFragment()
    }
}