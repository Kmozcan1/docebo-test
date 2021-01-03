package com.kmozcan1.docebotest.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.docebotest.databinding.PaginatedListViewBinding
import com.kmozcan1.docebotest.presentation.listener.PaginatedRecyclerViewScrollListener
import com.kmozcan1.docebotest.presentation.setAdapter


/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 *
 * Paginated list view with progress bar and callbacks
 */
class PaginatedListView : ConstraintLayout {

    lateinit var binding: PaginatedListViewBinding
        private set

    var isLoading = false

    var onFinalPage = false

    private var callbackListener: CallbackListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        binding = PaginatedListViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setAdapter(
        layoutManager: RecyclerView.LayoutManager,
        adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
    ) {
        binding.paginatedRecyclerView.setAdapter(layoutManager, adapter)
    }


    // Fragment calls this to set the callback listener
    fun setCallbackListener(callbackListener: CallbackListener) {
        this.callbackListener = callbackListener
        setOnScrollListener()
    }

    // Sets the scroll listener of the recyclerview
    private fun setOnScrollListener() {
        binding.paginatedRecyclerView.addOnScrollListener(
            PaginatedViewScrollListener(callbackListener!!)
        )

    }

    fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            binding.progressBar.visibility = VISIBLE
        } else {
            binding.progressBar.visibility = GONE
        }
        isLoading = isVisible
    }

    /**
     * Extends [PaginatedRecyclerViewScrollListener] to call [Listener] methods
     */
    inner class PaginatedViewScrollListener(
        private val callbackListener: CallbackListener
    ): PaginatedRecyclerViewScrollListener() {
        override fun onFinalItemVisible() {
            if (!onFinalPage && !isLoading) {
                callbackListener.onPaginatedListFinalItemVisible()
            }
        }

    }

    /**
     * Listener interface to make callbacks to the fragment using this view
     */
    interface CallbackListener {
        fun onPaginatedListFinalItemVisible()
        fun onPaginatedListItemClick(userName: String)
    }

}