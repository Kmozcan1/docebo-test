package com.kmozcan1.docebotest.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.ProfileFragmentBinding
import com.kmozcan1.docebotest.presentation.ArgConstants.USER_NAME_ARG
import com.kmozcan1.docebotest.presentation.viewmodel.HomeViewModel
import com.kmozcan1.docebotest.presentation.viewmodel.ProfileViewModel
import com.kmozcan1.docebotest.presentation.viewstate.HomeViewState
import com.kmozcan1.docebotest.presentation.viewstate.ProfileViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    lateinit var userName: String

    override fun layoutId() = R.layout.profile_fragment

    override fun getViewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun onViewBound() {
        arguments?.takeIf {
            it.containsKey(USER_NAME_ARG)
        }?.apply {
            userName = getString(USER_NAME_ARG).toString()
        }
    }

    override fun observeLiveDate() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getUserProfile(userName)
    }

    private fun viewStateObserver() = Observer<ProfileViewState> { viewState ->
        when (viewState.state) {
            ProfileViewState.State.USER_RESULT -> {
                // Hide progress bar
                with(viewState.userProfileModel!!) {
                    binding.fullNameTextView.text = fullName
                    binding.userNameTextView.text = userName
                    binding.urlTextView.text = profileUrl

                    activity?.let {
                        Glide.with(it)
                            .load(avatarUrl)
                            .placeholder(R.drawable.ic_placeholder_avatar)
                            .into(binding.avatarImageView)
                    }
                }

            }
            ProfileViewState.State.ERROR -> {
                makeToast(viewState.errorMessage)
            }
            ProfileViewState.State.LOADING -> {
                makeToast("LOADING")
            }
        }
    }

}