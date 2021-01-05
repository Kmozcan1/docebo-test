package com.kmozcan1.docebotest.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kmozcan1.docebotest.R


/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 *
 * Utilities class for extensions and binding adapters
 */

// SetAdapter extension for better code readability
fun RecyclerView.setAdapter(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}

//
@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_placeholder_avatar)
        .into(view)
}


