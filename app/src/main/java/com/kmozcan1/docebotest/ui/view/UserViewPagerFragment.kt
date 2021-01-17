package com.kmozcan1.docebotest.ui.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.UserViewPagerFragmentBinding
import com.kmozcan1.docebotest.ui.adapter.UserViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */

@AndroidEntryPoint
class UserViewPagerFragment : Fragment() {

    companion object {
        fun newInstance() = UserViewPagerFragment()
    }

    private lateinit var viewPager: ViewPager2

    lateinit var userViewPagerAdapter: UserViewPagerAdapter

    lateinit var binding: UserViewPagerFragmentBinding

    private val args: UserViewPagerFragmentArgs by navArgs()

    private val appCompatActivity: AppCompatActivity by lazy {
        activity as AppCompatActivity
    }

    val navController by lazy {
        findNavController()
    }

    val appBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    private val tabTitleList by lazy {
        listOf(getString(R.string.Profile), getString(R.string.Repositories))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserViewPagerFragmentBinding.inflate(
            inflater, container, false
        )
        setToolbar()
        setViewPager()
        return binding.root
    }

    private fun setViewPager() {
        userViewPagerAdapter = UserViewPagerAdapter(this, args.userName)
        viewPager = binding.userPager
        viewPager.adapter = userViewPagerAdapter

        val tabLayout = binding.userTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitleList[position]
        }.attach()
    }

    // Sets the toolbar with options menu
    private fun setToolbar() {
        binding.profileToolbar.setupWithNavController(navController, appBarConfiguration)
        appCompatActivity.setSupportActionBar(binding.profileToolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.profileToolbar.setNavigationOnClickListener { navController.navigateUp() }
        binding.profileToolbar.title = args.userName
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

}