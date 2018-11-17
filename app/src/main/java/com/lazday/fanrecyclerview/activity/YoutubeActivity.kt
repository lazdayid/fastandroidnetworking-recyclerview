package com.lazday.fanrecyclerview.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.lazday.fanrecyclerview.R

class YoutubeActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {
    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
        p1!!.cueVideo(bundle["KEY"].toString())
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        bundle = intent.extras

        val frag = supportFragmentManager.findFragmentById(R.id.youtube_frag) as YouTubePlayerSupportFragment?
        frag!!.initialize("AIzaSyBbLY_hn2_8KkfTbN4YFM7io6GyKw9EHr8", this)

        supportActionBar!!.title = bundle["NAME"].toString()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
