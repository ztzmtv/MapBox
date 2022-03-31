package com.example.infograce.recyclerview

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.R
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.Layers
import com.example.infograce.databinding.LayerGroupBinding

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var items: MutableList<Layers> = ArrayList()

    class ViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val binding = LayerGroupBinding.bind(itemView)
        fun bind(layers: Layers) = with(binding){
            titleView.text = layers.title
            transView.text = layers.trans
            syncView.text = layers.sync
            elemView.text = layers.elem
            zoomView.text = layers.zoom
            iconView.setImageResource(layers.icon)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem)

        val isVisible: Boolean = currentItem.visibility
        holder.binding.expandable.visibility = if (isVisible) View.VISIBLE else View.GONE

        if (isVisible) {
            holder.binding.titleView.setTypeface(null, Typeface.BOLD)
            holder.binding.titleView.setTextColor(Color.parseColor("#59BD87"))
            holder.binding.iconView.setColorFilter(Color.parseColor("#59BD87"))
        }

        holder.binding.chevron.setOnClickListener{
            currentItem.visibility =! currentItem.visibility
            notifyItemChanged(position)
        }

        holder.binding.slider.addOnChangeListener { slider, value, fromUser ->
            holder.binding.transView.setText("Прозрачность: ${value.toInt()}%")
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layer_group, parent,false)
        return ViewHolder(view)
    }

    fun submitList(list: MutableList<Layers>){
        items = list

    }

    fun addLayer(){
        items.add(DataSource.addLayer[0])
        notifyDataSetChanged()
    }

    fun removeLayer(){
        items.remove(items.removeAt(items.lastIndex))
        notifyDataSetChanged()
    }



}