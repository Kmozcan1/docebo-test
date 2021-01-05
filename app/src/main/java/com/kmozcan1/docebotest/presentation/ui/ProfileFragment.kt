package com.kmozcan1.docebotest.presentation.ui

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
            State.USER_RESULT -> {
                //TODO Hide progress bar
                with(viewState.userProfileModel!!) {
                    // Set view TextView texts
                    with(binding) {
                        fullNameTextView.text = fullName
                        userNameTextView.text = userName
                        urlTextView.text = profileUrl
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
                makeToast("LOADING")
            }
        }
    }

}