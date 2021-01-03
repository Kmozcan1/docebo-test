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

    override fun layoutId() = R.layout.profile_fragment

    override fun getViewModelClass(): Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun onViewBound() {
    }

    override fun observeLiveDate() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getUserProfile("kmozcan1")
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