package com.example.baker_street.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baker_street.databinding.ListItemAnnouncementsBinding
import com.example.baker_street.models.AnnouncementModel

class StreamFragmentAdapter : RecyclerView.Adapter<StreamFragmentAdapter.MyViewHolder>() {

    var data = ArrayList<AnnouncementModel>()

    private var onAnnouncementClickListener: OnAnnouncementClickListener? = null

    inner class MyViewHolder(val binding: ListItemAnnouncementsBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(announcementModel: ArrayList<AnnouncementModel>?) {
        this.data.clear()
        if (announcementModel != null) {
            this.data.addAll(announcementModel)
        }
        notifyDataSetChanged()
    }

    fun setOnAnnouncementListener(onAnnouncementClickListener: OnAnnouncementClickListener) {
        this.onAnnouncementClickListener = onAnnouncementClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemAnnouncementsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.announcement.text = data[position].description
        holder.binding.root.setOnClickListener {
            onAnnouncementClickListener?.onAnnouncementClick(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnAnnouncementClickListener {
        fun onAnnouncementClick(pos: Int)
    }
}