package com.kmozcan1.docebotest.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
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

// Binding adapter that loads image url with glide
@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.ic_placeholder_avatar)
        .into(view)
}

// Fragment extension to hide the keyboard
fun Fragment.hideKeyboard() {
    if (activity?.currentFocus != null) {
        val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }
}



