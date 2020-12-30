package com.kmozcan1.docebotest.application.di

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**
 * Created by Kadir Mert Özcan on 29-Dec-20.
 */

@Module
@InstallIn(FragmentComponent::class)
object UIModule {

    @Provides
    fun providesNavController(
        fragment: Fragment
    ): NavController {
        return fragment.findNavController()
    }

    @Provides
    fun providesAppBarConfiguration(
        navController: NavController
    ): AppBarConfiguration {
        return AppBarConfiguration(navController.graph)
    }
}