package com.example.baker_street.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baker_street.databinding.ListItemAssignmentsBinding
import com.example.baker_street.models.AssignmentModel

class AssignmentsFragmentAdapter : RecyclerView.Adapter<AssignmentsFragmentAdapter.MyViewHolder>() {

    var data = ArrayList<AssignmentModel>()

    private var onAssignmentClickListener: OnAssignmentClickListener? = null

    inner class MyViewHolder(val binding: ListItemAssignmentsBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(assignmentModel: ArrayList<AssignmentModel>?) {
        this.data.clear()
        if (assignmentModel != null) {
            this.data.addAll(assignmentModel)
        }
        notifyDataSetChanged()
    }

    fun setOnAssignmentListener(onAssignmentClickListener: OnAssignmentClickListener) {
        this.onAssignmentClickListener = onAssignmentClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemAssignmentsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.announcement.text = data[position].text
        holder.binding.profName.text = data[position].courseid
        holder.binding.root.setOnClickListener {
            onAssignmentClickListener?.onAssignmentClick(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnAssignmentClickListener {
        fun onAssignmentClick(pos: Int)
    }
}