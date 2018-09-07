package com.lazday.fanrecyclerview.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lazday.fanrecyclerview.R
import com.lazday.fanrecyclerview.model.Movie
import kotlinx.android.synthetic.main.adapter_main.view.*
import java.util.ArrayList

class MainAdapter(private val movies: ArrayList<Movie>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.text = movies[position].title
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
            Toast.makeText(v!!.context, txtTitle.text, Toast.LENGTH_SHORT).show()
        }

        init {
            itemView.setOnClickListener(this)
        }

        val txtTitle = itemView.txtTitle!!

    }
}