package com.lazday.fanrecyclerview.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.lazday.fanrecyclerview.R
import com.lazday.fanrecyclerview.adapter.MainAdapter
import com.lazday.fanrecyclerview.adapter.PopularAdapter
import com.lazday.fanrecyclerview.adapter.UpcomingAdapter
import com.lazday.fanrecyclerview.data.Constant
import com.lazday.fanrecyclerview.data.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val movies = ArrayList<Movie>()
    val popular = ArrayList<Movie>()
    val upcoming = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMovie()
        getPopular()
        getUpcoming()

        rcvNowPlaying.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        rcvPopular.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        rcvUpcoming.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
    }

    fun getMovie(){

        movies.clear()
        rcvNowPlaying.adapter = null
        relativeNowPlaying.visibility = VISIBLE

        Log.e("_logUrl", "${Constant.tmdb_base_url}now_playing?api_key=${Constant.tmdb_api_key}&language=en-US&page=1");
        AndroidNetworking.get( "${Constant.tmdb_base_url}now_playing?api_key=${Constant.tmdb_api_key}&language=en-US&page=1")
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

                        rcvNowPlaying.adapter = MainAdapter(this@MainActivity, movies)
                        relativeNowPlaying.visibility = GONE

                    }

                    override fun onError(anError: ANError) {
                        Log.i("_err", anError.toString())
                    }
                })
    }

    fun getPopular(){

        popular.clear()
        rcvPopular.adapter = null
        relativePopular.visibility = VISIBLE

        Log.e("_logUrl", "${Constant.tmdb_base_url}popular?api_key=${Constant.tmdb_api_key}&language=en-US&page=1");
        AndroidNetworking.get( "${Constant.tmdb_base_url}popular?api_key=${Constant.tmdb_api_key}&language=en-US&page=1")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.e("_kotlinResponse", response.toString())

                        val jsonArray = response.getJSONArray("results")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            Log.e("_kotlinTitle", jsonObject.optString("title"))

                            popular.add(
                                    Movie(jsonObject.getInt("id"), jsonObject.getString("title"),
                                            jsonObject.getString("release_date"), jsonObject.getString("backdrop_path"))
                            )

                        }

                        rcvPopular.adapter = PopularAdapter(this@MainActivity, popular)
                        relativePopular.visibility = GONE

                    }

                    override fun onError(anError: ANError) {
                        Log.i("_err", anError.toString())
                    }
                })
    }

    fun getUpcoming(){

        upcoming.clear()
        rcvUpcoming.adapter = null
        relativeUpcoming.visibility = VISIBLE

        Log.e("_logUrl", "${Constant.tmdb_base_url}upcoming?api_key=${Constant.tmdb_api_key}&language=en-US&page=1");
        AndroidNetworking.get( "${Constant.tmdb_base_url}upcoming?api_key=${Constant.tmdb_api_key}&language=en-US&page=1")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.e("_kotlinResponse", response.toString())

                        val jsonArray = response.getJSONArray("results")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            Log.e("_kotlinTitle", jsonObject.optString("title"))

                            upcoming.add(
                                    Movie(jsonObject.getInt("id"), jsonObject.getString("title"),
                                            jsonObject.getString("release_date"), jsonObject.getString("backdrop_path"))
                            )

                        }

                        rcvUpcoming.adapter = UpcomingAdapter(this@MainActivity, upcoming)
                        relativeUpcoming.visibility = GONE

                    }

                    override fun onError(anError: ANError) {
                        Log.i("_err", anError.toString())
                    }
                })
    }
}
