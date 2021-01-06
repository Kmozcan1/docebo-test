package com.kmozcan1.docebotest.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.kmozcan1.docebotest.R
import com.kmozcan1.docebotest.databinding.RepositoryListItemBinding
import com.kmozcan1.docebotest.domain.model.RepositoryModel
import com.kmozcan1.docebotest.domain.model.UserSearchItemModel
import com.kmozcan1.docebotest.presentation.ui.PaginatedListView
import java.text.NumberFormat
import java.util.*

/**
 * Created by Kadir Mert Özcan on 05-Jan-21.
 */
class RepositoryListAdapter(private val repositoryList: MutableList<RepositoryModel>,
                             private val callbackListener: PaginatedListView.CallbackListener):
    RecyclerView.Adapter<RepositoryListAdapter.RepositoryListItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryListItemViewHolder {
        // Inflate with data binding
        val binding = RepositoryListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoryListItemViewHolder, position: Int) {
        with(repositoryList[position]) {
            holder.bind(
                name = name,
                stars = starCount,
                // Star image source is based on number of stars
                starSrc = when (starCount) {
                    in 100..Int.MAX_VALUE -> {
                        R.drawable.ic_star
                    }
                    in 50 until 100 -> {
                        R.drawable.ic_star_half_full
                    }
                    else -> {
                        R.drawable.ic_star_outline
                    }
                }
            )
        }

    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    inner class RepositoryListItemViewHolder(
        private val binding: RepositoryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        // Binds the name, star count, star image source and the callback listener
        fun bind(name: String, stars: Int, @DrawableRes starSrc: Int) {
            binding.apply {
                repositoryName = name
                starCount = NumberFormat.getNumberInstance(Locale.US).format(stars)
                starImageView.setImageResource(starSrc)
                listener = callbackListener
            }
        }
    }

    // Adds a new batch of repositories to the RecyclerView
    fun addRepositories(repositories: List<RepositoryModel>) {
        val startPosition = itemCount
        repositoryList.addAll(repositories)
        notifyItemRangeInserted(startPosition, repositoryList.size)
    }

    // Clears the RecyclerView data
    fun clearRepositoryList() {
        repositoryList.clear()
        notifyDataSetChanged()
    }

}