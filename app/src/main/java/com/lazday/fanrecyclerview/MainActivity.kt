package com.lazday.fanrecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.lazday.fanrecyclerview.adapter.MainAdapter
import com.lazday.fanrecyclerview.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val movies = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMovie()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        swipeRefresh.setOnRefreshListener {
            getMovie()
        }
    }

    fun getMovie(){

        movies.clear()
        recyclerView.adapter = null
        swipeRefresh.isRefreshing = true

        AndroidNetworking.get("http://api.themoviedb.org/3/movie/popular?api_key=${getString(R.string.api_key)}&language=en-US&page=1")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.e("_kotlinResponse", response.toString())

                        val jsonArray = response.getJSONArray("results")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            Log.e("_kotlinTitle", jsonObject.optString("title"))

                            movies.add(
                                    Movie(jsonObject.getInt("id"), jsonObject.getString("title"),
                                            jsonObject.getString("release_date"), jsonObject.getString("poster_path"))
                            )

                        }

                        recyclerView.adapter = MainAdapter(this@MainActivity, movies)
                        swipeRefresh.isRefreshing = false

                    }

                    override fun onError(anError: ANError) {
                        Log.i("_err", anError.toString())
                    }
                })
    }
}
