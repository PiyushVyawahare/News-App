package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter

    }

    private fun fetchData() {

        val url = "https://drive.google.com/file/d/1IYNkevkorvuuTpDsY-mwIvr1kojhOkJb/view?usp=sharing"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                val newsJSONArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJSONArray.length()){
                    val newsJsonObject = newsJSONArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        )

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


//        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=1f4a12d2698e432ea9cf18126dcc7acd"
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET, url, null,
//
//            Response.Listener{
//                Toast.makeText(this,"No Error", Toast.LENGTH_LONG).show()
////                val newsJsonArray = it.getJSONArray("articles")
////                val newsArray = ArrayList<News>()
////                for(i in 0 until newsJsonArray.length()){
////                    val newsJsonObject = newsJsonArray.getJSONObject(i)
////
////                    val news = News(
////                        newsJsonObject.getString("title"),
////                        newsJsonObject.getString("author"),
////                        newsJsonObject.getString("url"),
////                        newsJsonObject.getString("urlToImage")
////                    )
////                    newsArray.add(news)
////                }
////                mAdapter.updateNews(newsArray)
//            },
//            Response.ErrorListener{
//                Toast.makeText(this,"Error", Toast.LENGTH_LONG).show()
//            }
//        )
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        Toast.makeText(this, "Item clicked", Toast.LENGTH_LONG).show()
    }
}