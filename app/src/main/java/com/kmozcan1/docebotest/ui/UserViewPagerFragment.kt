package com.kmozcan1.docebotest.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.UserViewPagerFragmentBinding
import com.kmozcan1.docebotest.presentation.adapter.UserViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */

@AndroidEntryPoint
class UserViewPagerFragment : Fragment() {
    private lateinit var viewPager: ViewPager2

    lateinit var userViewPagerAdapter: UserViewPagerAdapter

    lateinit var binding: UserViewPagerFragmentBinding

    val args: UserViewPagerFragmentArgs by navArgs()

    private val appCompatActivity: AppCompatActivity by lazy {
        activity as AppCompatActivity
    }

    @Inject
    lateinit var navController: NavController

    @Inject
    lateinit var appBarConfiguration: AppBarConfiguration

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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