package com.kmozcan1.docebotest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.docebotest.BR
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.UserListItemBinding
import com.kmozcan1.docebotest.domain.model.UserListItem
import com.kmozcan1.docebotest.presentation.bindingInflate
import com.kmozcan1.docebotest.ui.PaginatedListView

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
class UserListAdapter(private val userList: MutableList<UserListItem>,
                      private val callbackListener: PaginatedListView.CallbackListener):
        RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        private val binding: UserListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, avatarUrl: String) {
            binding.userName = name
            binding.avatarUrl = avatarUrl
            binding.listener = callbackListener
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