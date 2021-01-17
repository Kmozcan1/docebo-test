package com.kmozcan1.docebotest.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kmozcan1.docebotest.ui.ArgConstants.USER_NAME_ARG
import com.kmozcan1.docebotest.ui.view.ProfileFragment
import com.kmozcan1.docebotest.ui.view.RepositoriesFragment
import com.kmozcan1.docebotest.ui.view.UserViewPagerFragment
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