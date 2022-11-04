package com.example.newsapi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapi.databinding.ItemListNewsBinding

class NewsRecyclerView(private val context: Context) :
    RecyclerView.Adapter<NewsRecyclerView.ViewHolder>() {
    private var newList: NewsList? = null
    inner class ViewHolder(val binding: ItemListNewsBinding) :
        RecyclerView.ViewHolder(binding.root){
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecyclerView.ViewHolder {
        val binding =
            ItemListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsRecyclerView.ViewHolder, position: Int) {
        val item = newList?.articles?.get(position)
        Glide.with(context)
            .load(item?.urlToImage)
            .fitCenter()
            .into(holder.binding.image)
        holder.binding.author.text = item?.author
        holder.binding.date.text=item?.publishedAt
        holder.binding.description.text = item?.description
        holder.binding.title.text = item?.title
        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsDetails::class.java)
            intent.putExtra("image", item?.urlToImage)
            intent.putExtra("title", item?.title)
            intent.putExtra("description", item?.description)
            intent.putExtra("link", item?.url)
            intent.putExtra("content",item?.content)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newList?.articles?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(it: NewsList) {
        newList = it
        this@NewsRecyclerView.notifyDataSetChanged()
    }
}