package com.lazday.fanrecyclerview.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.lazday.fanrecyclerview.R
import com.lazday.fanrecyclerview.adapter.DetailAdapter
import com.lazday.fanrecyclerview.data.Constant
import com.lazday.fanrecyclerview.data.model.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.json.JSONObject
import java.util.ArrayList

class DetailActivity : AppCompatActivity() {

    private var bundle = Bundle()
    val videos = ArrayList<Video>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        bundle = intent.extras
        getDetail(bundle["MOVIE_ID"].toString())
        getVideo(bundle["MOVIE_ID"].toString())

        rcvVideo.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        supportActionBar!!.title = "Overview"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun getDetail(movie_id:String){
        AndroidNetworking.get( "${Constant.tmdb_base_url}$movie_id?api_key=${Constant.tmdb_api_key}&language=en-US")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.e("_kotlinResponse", response.toString())

                        Log.e("_backdrop", Constant.tmdb_path_poster + response.getString("backdrop_path"))
                        Picasso.get()
                                .load(Constant.tmdb_path_poster + response["backdrop_path"])
                                .placeholder(R.drawable.ic_filter_hdr)
                                .fit().into(imgBackdrop)

                        txtTitle.text       = response["original_title"].toString()
                        txtOverview.text    = response["overview"].toString()


                    }

                    override fun onError(anError: ANError) {
                        Log.i("_err", anError.toString())
                    }
                })
    }

    private fun getVideo(movie_id:String){
        AndroidNetworking.get( "${Constant.tmdb_base_url}$movie_id/videos?api_key=${Constant.tmdb_api_key}&language=en-US")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.e("_kotlinResponse", response.toString())

                        val jsonArray = response.getJSONArray("results")
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            Log.e("_logName", jsonObject.optString("name"))

                            videos.add(
                                    Video(jsonObject.getString("key"), jsonObject.getString("name"))
                            )

                        }

                        rcvVideo.adapter = DetailAdapter(this@DetailActivity, videos)
                        rcvVideo.isLayoutFrozen = true

                    }

                    override fun onError(anError: ANError) {
                        Log.i("_err", anError.toString())
                    }
                })
    }
}
