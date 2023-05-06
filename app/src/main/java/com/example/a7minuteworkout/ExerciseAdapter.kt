package com.example.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkout.databinding.ItemLayoutBinding

class ExerciseAdapter(val list:ArrayList<ExerciseModel>):RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(val itemBinding:ItemLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item=list[position]
        holder.itemBinding.tvItem.text=item.id.toString()

        when{
            item.isSelected==true ->{
                holder.itemBinding.tvItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.current_itemview)
                holder.itemBinding.tvItem.setTextColor(Color.parseColor("#212121"))
            }
            item.isCompleted==true ->{
                holder.itemBinding.tvItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_bg)
                holder.itemBinding.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else->{
                holder.itemBinding.tvItem.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.itemview_bg)
                holder.itemBinding.tvItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}