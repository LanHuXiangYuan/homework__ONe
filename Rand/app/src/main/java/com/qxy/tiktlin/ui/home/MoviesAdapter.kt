package com.qxy.tiktlin.home.ui



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.qxy.tiktlin.databinding.ItemMovieBinding
import com.rdc.myapplication.model.bean.MovieBean



class MoviesAdapter(
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<MovieBean, MovieViewHolder>(SessionDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ).apply {
            lifecycleOwner = this@MoviesAdapter.lifecycleOwner
        }
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.movie = getItem(position)
        holder.binding.executePendingBindings()
    }
}

class MovieViewHolder(
    internal val binding: ItemMovieBinding
) : ViewHolder(binding.root)

object SessionDiff : DiffUtil.ItemCallback<MovieBean>() {
    override fun areItemsTheSame(oldItem: MovieBean, newItem: MovieBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieBean, newItem: MovieBean): Boolean {
        return oldItem == newItem
    }
}