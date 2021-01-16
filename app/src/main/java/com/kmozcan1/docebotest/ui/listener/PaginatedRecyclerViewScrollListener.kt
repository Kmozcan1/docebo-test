package com.kmozcan1.docebotest.ui.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
abstract class PaginatedRecyclerViewScrollListener: RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (lastVisibleItem == layoutManager.itemCount - 1) {
            onFinalItemVisible()
        }


        super.onScrolled(recyclerView, dx, dy)
    }

    abstract fun onFinalItemVisible()
}