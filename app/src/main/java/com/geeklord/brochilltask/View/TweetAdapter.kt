package com.geeklord.brochilltask.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geeklord.brochilltask.Model.GetTweetResponse
import com.geeklord.brochilltask.databinding.TweetItemBinding

class TweetAdapter(private val TweetList : List<GetTweetResponse>) : ListAdapter<GetTweetResponse, TweetAdapter.ListViewHolder>(ComparatorDiffUtil())
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetAdapter.ListViewHolder {
        val binding = TweetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TweetAdapter.ListViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    inner class ListViewHolder(private val binding: TweetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetTweetResponse) {
            binding.tweetTitle.text = item.tweet
        }
    }


    class ComparatorDiffUtil : DiffUtil.ItemCallback<GetTweetResponse>() {
        override fun areItemsTheSame(oldItem: GetTweetResponse, newItem: GetTweetResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetTweetResponse, newItem: GetTweetResponse): Boolean {
            return oldItem == newItem
        }
    }

}