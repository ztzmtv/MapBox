package com.example.infograce.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.infograce.MainActivity
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayerGroupBinding
import com.example.infograce.databinding.LayersGroupBinding
import com.example.infograce.databinding.MainActivityBinding

sealed class RecyclerViewHolders(binding: ViewBinding, listenerActivity: MainActivity): RecyclerView.ViewHolder(binding.root) {

    class LayersGroupViewHolder(private val binding: LayersGroupBinding,private val listenerActivity: MainActivity): RecyclerViewHolders(binding, listenerActivity){
        fun bind(title: RecyclerViewItems.LayersGroup, listenerActivity: MainActivity){
            binding.textViewTitle.text = title.titleGroup
        }
    }

    class LayersViewHolder(val binding: LayerGroupBinding, private val listenerActivity: MainActivity): RecyclerViewHolders(binding,listenerActivity){
        fun bind(layers: RecyclerViewItems.Layers, listenerActivity: MainActivity) = with(binding){
            titleView.text = layers.title.title
            transView.text = layers.trans
            syncView.text = layers.sync
            elemView.text = layers.elem
            zoomView.text = layers.zoom
            iconView.setImageResource(layers.icon)

            switch2.setOnClickListener {
                layers.switchSave = switch2.isChecked
                listenerActivity.onSwitched()
            }
            switch2.setOnCheckedChangeListener { buttonView, isChecked ->
                layers.switch = isChecked
            }
        }
    }

}