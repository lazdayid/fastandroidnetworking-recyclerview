package com.lazday.fanrecyclerview.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lazday.fanrecyclerview.R
import com.lazday.fanrecyclerview.activity.DetailActivity
import com.lazday.fanrecyclerview.data.Constant
import com.lazday.fanrecyclerview.data.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_popular.view.*
import java.util.ArrayList

class PopularAdapter (val context: Context, val movies: ArrayList<Movie>): RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtId.text = movies[position].id.toString()
        holder.txtTitle.text = movies[position].title
        holder.txtDate.text = "― " + movies[position].release
        Picasso.get().load(Constant.tmdb_path_poster + movies[position].poster)
                .placeholder(R.drawable.ic_filter_hdr)
                .error(R.drawable.ic_filter_hdr)
                .into(holder.imgPoster)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_popular, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
            val intent = Intent(v!!.context, DetailActivity::class.java)
            intent.putExtra("TITLE", txtTitle.text)
            intent.putExtra("MOVIE_ID", txtId.text)
            v.context.startActivity(intent)
        }

        init {
            itemView.setOnClickListener(this)
        }

        val txtTitle = itemView.txtTitle!!
        val txtId = itemView.txtId!!
        val txtDate = itemView.txtDate!!
        val imgPoster = itemView.imgPoster!!

    }
}