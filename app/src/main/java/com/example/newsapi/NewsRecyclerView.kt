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
        RecyclerView.ViewHolder(binding.root) {
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
        item?.let { articles ->
            val authorName = "Author Name: ${articles.author}"
            val source = "Source: ${articles.source.name}"
            val date =
                "Date: ${articles.publishedAt.parseDate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy")}"
            holder.binding.author.text = authorName
            holder.binding.date.text = date
            holder.binding.description.text = articles.description
            holder.binding.title.text = articles.title
            holder.binding.source.text = source
            holder.itemView.setOnClickListener {
                val intent = Intent(context, NewsDetails::class.java)
                intent.putExtra("image", articles.urlToImage)
                intent.putExtra("title", articles.title)
                intent.putExtra("description", articles.description)
                intent.putExtra("link", articles.url)
                intent.putExtra("content", articles.content)
                context.startActivity(intent)
            }
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