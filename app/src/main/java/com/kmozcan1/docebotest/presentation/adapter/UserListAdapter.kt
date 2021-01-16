package com.kmozcan1.docebotest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.docebotest.databinding.UserListItemBinding
import com.kmozcan1.docebotest.domain.model.UserSearchItemModel
import com.kmozcan1.docebotest.ui.PaginatedListView

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
class UserListAdapter(val userList: MutableList<UserSearchItemModel>,
                      private val callbackListener: PaginatedListView.CallbackListener):
        RecyclerView.Adapter<UserListAdapter.UserListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        // Inflate with data binding
        val binding = UserListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) {
        with(userList[position]) {
            holder.bind(userName, avatarUrl)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    // Binds the username, avatarUrl and callback listener
    inner class UserListItemViewHolder(
        private val binding: UserListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, avatarUrl: String) {
            binding.userName = name
            binding.avatarUrl = avatarUrl
            binding.listener = callbackListener
        }
    }

    // Adds a new batch of search results to the RecyclerView
    fun addSearchResult(searchResult: List<UserSearchItemModel>) {
        val startPosition = itemCount
        userList.addAll(searchResult)
        notifyItemRangeInserted(startPosition, searchResult.size)
    }

    // Clears the RecyclerView data
    fun clearSearchResults() {
        userList.clear()
        notifyDataSetChanged()
    }
}