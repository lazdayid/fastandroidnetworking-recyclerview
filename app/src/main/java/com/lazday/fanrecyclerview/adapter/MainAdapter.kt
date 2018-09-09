package com.lazday.fanrecyclerview.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lazday.fanrecyclerview.DetailActivity
import com.lazday.fanrecyclerview.R
import com.lazday.fanrecyclerview.model.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_main.view.*
import java.util.ArrayList

class MainAdapter(val context: Context, val movies: ArrayList<Movie>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtId.text = movies[position].id.toString()
        holder.txtTitle.text = movies[position].title
        holder.txtDate.text = movies[position].release
        holder.txtDate.text = movies[position].release
        Picasso.get().load(context.getString(R.string.base_path_poster) + movies[position].poster)
                .placeholder(R.drawable.ic_filter_hdr)
                .centerCrop().fit()
                .into(holder.imgPoster)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)
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