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
import com.lazday.fanrecyclerview.adapter.PopularAdapter
import com.lazday.fanrecyclerview.data.Constant
import com.lazday.fanrecyclerview.data.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val popular = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPopular()
        rcvPopular.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL, false)
    }

    fun getPopular(){

        popular.clear()
        rcvPopular.adapter = null
        relativePopular.visibility = VISIBLE

        Log.e("_logUrl", "${Constant.tmdb_base_url}popular?api_key=${Constant.tmdb_api_key}&language=en-US&page=1")
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
}
