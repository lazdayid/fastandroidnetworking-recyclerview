package com.lazday.fanrecyclerview.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lazday.fanrecyclerview.R
import com.lazday.fanrecyclerview.activity.YoutubeActivity
import com.lazday.fanrecyclerview.data.model.Video
import kotlinx.android.synthetic.main.adapter_detail.view.*

class DetailAdapter(val context: Context, val videos: ArrayList<Video>): RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = videos[position].name
        holder.txtKey.text = videos[position].key
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_detail, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            val intent = Intent(v!!.context, YoutubeActivity::class.java)
            intent.putExtra("KEY", txtKey.text)
            intent.putExtra("NAME", txtName.text)
            v!!.context.startActivity(intent)
//            Toast.makeText(v!!.context, txtName.text, Toast.LENGTH_SHORT).show()
        }
        init {
            itemView.setOnClickListener(this)
        }

        val txtName = itemView.txtName
        val txtKey  = itemView.txtKey
    }
}