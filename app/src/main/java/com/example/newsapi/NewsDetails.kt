package com.example.newsapi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapi.databinding.ActivityNewsDetailsBinding


class NewsDetails : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarBackButton.setOnClickListener { onBackPressed() }
        val image = intent.getStringExtra("image").orEmpty()
        val link = intent.getStringExtra("link").orEmpty()
        val description = intent.getStringExtra("description").orEmpty()
        val title = intent.getStringExtra("title").orEmpty()
        val content = intent.getStringExtra("content").orEmpty()

        binding.imageView.visibility = View.VISIBLE
        Glide.with(this)
            .load(image)
            .fitCenter()
            .into(binding.imageView)

        binding.descriptionDetail.text = description
        binding.titleDetail.text = title
        binding.link.text = link
        binding.contentDetail.text = content

        binding.link.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(browserIntent)
        }
    }
}