package com.nisaefendioglu.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nisaefendioglu.movieapp.databinding.ItemRecyclerBinding
import com.nisaefendioglu.movieapp.model.DataModel


class MovieAdapter(private val itemModel: MutableList<DataModel>) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    var onItemClick: ((DataModel) -> Unit)? = null

    inner class MyViewHolder(val viewDataBinding: ItemRecyclerBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = itemModel[position]

        Glide.with(holder.itemView.context).load(data.poster).into(holder.viewDataBinding.itemImage)
        holder.viewDataBinding.itemName.text = data.title
        holder.viewDataBinding.executePendingBindings()
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(data)
        }
    }

    override fun getItemCount(): Int {
        return itemModel.size
    }

    fun updateData(list: List<DataModel>?, clear: Boolean = false) {
        list?.let {
            if (clear) {
                itemModel.clear()
                notifyDataSetChanged()
            }
            itemModel.addAll(list)
        } ?: kotlin.run {
            itemModel.clear()
        }
        notifyDataSetChanged()
    }
}
