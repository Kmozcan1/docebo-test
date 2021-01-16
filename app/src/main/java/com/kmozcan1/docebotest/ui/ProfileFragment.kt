package com.kmozcan1.docebotest.ui

import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.ProfileFragmentBinding
import com.kmozcan1.docebotest.presentation.ArgConstants.USER_NAME_ARG
import com.kmozcan1.docebotest.presentation.viewmodel.ProfileViewModel
import com.kmozcan1.docebotest.presentation.viewstate.ProfileViewState
import com.kmozcan1.docebotest.presentation.viewstate.ProfileViewState.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileFragmentBinding, ProfileViewModel>() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var userName: String

    override val layoutId = R.layout.profile_fragment

    override val viewModelClass: Class<ProfileViewModel> = ProfileViewModel::class.java

    override fun onViewBound() {
        // Get the user name from bundle
        arguments?.takeIf {
            it.containsKey(USER_NAME_ARG)
        }?.apply {
            userName = getString(USER_NAME_ARG).toString()
        }
    }

    override fun observeLiveData() {
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver())
        viewModel.getUserProfile(userName)
    }

    private fun viewStateObserver() = Observer<ProfileViewState> { viewState ->
        when (viewState.state) {
            State.USER_RESULT -> {
                //TODO Hide progress bar
                with(viewState.userProfileModel!!) {
                    // Set view TextView texts and hide progress bar
                    with(binding) {
                        profileProgressBar.visibility = View.GONE
                        fullNameTextView.text = fullName
                        userNameTextView.text = userName
                        urlTextView.text = profileUrl
                        emailTextView.text = email
                        locationTextView.text = location
                    }

                    activity?.let {
                        Glide.with(it)
                            .load(avatarUrl)
                            .placeholder(R.drawable.ic_placeholder_avatar)
                            .into(binding.avatarImageView)
                    }
                }

            }
            State.ERROR -> {
                makeToast(viewState.errorMessage)
            }
            State.LOADING -> {
                binding.profileProgressBar.visibility = View.VISIBLE
            }
        }
    }

}