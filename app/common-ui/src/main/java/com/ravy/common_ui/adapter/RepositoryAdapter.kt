package com.ravy.common_ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ravy.common_ui.databinding.ItemGithubRepositoryBinding
import com.ravy.data.vo.Repository
import com.ravy.data.vo.User

class RepositoryAdapter(
    private val viewModel: RepositoryAdapterViewModel
) : PagingDataAdapter<RepositoryAdapterItem, RepositoryAdapterViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<RepositoryAdapterItem>() {

        override fun areItemsTheSame(
            oldItem: RepositoryAdapterItem,
            newItem: RepositoryAdapterItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RepositoryAdapterItem,
            newItem: RepositoryAdapterItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryAdapterViewHolder {
        return RepositoryAdapterViewHolder(
            ItemGithubRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            viewModel
        )
    }

    override fun onBindViewHolder(holder: RepositoryAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class RepositoryAdapterViewHolder(
    private val binding: ItemGithubRepositoryBinding,
    viewModel: RepositoryAdapterViewModel
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.viewModel = viewModel
    }

    fun bind(item: RepositoryAdapterItem?) {
        binding.item = item
        binding.executePendingBindings()
    }
}

data class RepositoryAdapterItem(
    val repository: Repository,
    val owner: User = repository.owner
) {
    val id: Long = repository.id
    val name: String = repository.name
    val desc: String = repository.description
    val imageUrl: String = owner.avatarUrl
}