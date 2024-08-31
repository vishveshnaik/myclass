package com.example.baker_street.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baker_street.databinding.ListItemPeopleBinding
import com.example.baker_street.models.UserModel

class PeopleFragmentAdapter : RecyclerView.Adapter<PeopleFragmentAdapter.MyViewHolder>() {

    var data = ArrayList<UserModel>()

    inner class MyViewHolder(val binding: ListItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(data: ArrayList<UserModel>) {
        this.data.clear()
        this.data.addAll(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ListItemPeopleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.displayName.text = data[position].admno + " " + data[position].name
    }

    override fun getItemCount(): Int {
        return data.size
    }
}