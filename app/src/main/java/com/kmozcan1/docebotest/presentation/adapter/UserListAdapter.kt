package com.kmozcan1.docebotest.presentation.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.docebotest.BR
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.domain.model.UserListItem
import com.kmozcan1.docebotest.presentation.bindingInflate

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
class UserListAdapter(private val userList: MutableList<UserListItem>):
        RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val binding = bindingInflate(
            parent,
            R.layout.user_list_item
        )
        return UserListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        holder.bind(
            userList[position].userName,
            userList[position].avatarUrl
        )

    }

    inner class UserListItemViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, avatarUrl: String) {
            binding.setVariable(BR.userName, name)
            binding.setVariable(BR.avatarUrl, avatarUrl)
            binding.executePendingBindings()
        }
    }

    fun addSearchResult(searchResult: List<UserListItem>) {
        val startPosition = itemCount
        userList.addAll(searchResult)
        notifyItemRangeInserted(startPosition, searchResult.size)
    }

    fun clearSearchResults() {
        userList.clear()
        notifyDataSetChanged()
    }
}