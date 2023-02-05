package com.example.newsapi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Callback<NewsList> {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Constants.isNetworkAvailable(this)) {
            Network.API.getNewsList().enqueue(this@MainActivity)
            binding.revList.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.reloadButton.visibility = View.GONE
        } else {
            Toast.makeText(this, "no internet connection", Toast.LENGTH_SHORT).show()
            binding.revList.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.reloadButton.visibility = View.VISIBLE
        }
        binding.revList.layoutManager = LinearLayoutManager(this)
        adapter = NewsRecyclerView(this)
        binding.revList.adapter = adapter
        binding.reloadButton.setOnClickListener {
            this.recreate()
        }
    }

    override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
        response.body()?.let {
            if (Constants.isNetworkAvailable(this)) {
                if (it.articles.isNotEmpty()) {
                    adapter.update(it)
                }
            }
            Log.i("news ", it.articles.toString())
        }
    }

    override fun onFailure(call: Call<NewsList>, t: Throwable) {
        t.message?.let { Log.i("Error", it) }
    }

}

