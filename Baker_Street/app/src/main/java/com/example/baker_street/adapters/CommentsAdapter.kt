package com.example.baker_street.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baker_street.databinding.ListItemCommentsBinding
import com.example.baker_street.models.CommentModel

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.MyViewHolder>() {

    var data = ArrayList<CommentModel>()

    inner class MyViewHolder(val binding: ListItemCommentsBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(commentModel: ArrayList<CommentModel>?) {
        this.data.clear()
        if (commentModel != null) {
            this.data.addAll(commentModel)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemCommentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.commentDescription.text = data[position].text
        holder.binding.studName.text = data[position].publisher_id
    }

    override fun getItemCount(): Int {
        return data.size
    }

}