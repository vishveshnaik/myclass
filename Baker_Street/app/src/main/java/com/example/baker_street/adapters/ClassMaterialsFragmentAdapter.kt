package com.example.baker_street.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.baker_street.databinding.ListItemClassMaterialsBinding
import com.example.baker_street.models.MaterialModel

class ClassMaterialsFragmentAdapter :
    RecyclerView.Adapter<ClassMaterialsFragmentAdapter.MyViewHolder>() {

    var data = ArrayList<MaterialModel>()

    inner class MyViewHolder(val binding: ListItemClassMaterialsBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setList(materialModel: ArrayList<MaterialModel>?) {
        this.data.clear()
        if (materialModel != null) {
            this.data.addAll(materialModel)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemClassMaterialsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.binding.announcement.text = data[position].description
    }

    override fun getItemCount(): Int {
        return data.size
    }
}