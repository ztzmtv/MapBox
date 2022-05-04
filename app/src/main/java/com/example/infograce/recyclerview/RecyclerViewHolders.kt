package com.example.infograce.recyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayerGroupBinding
import com.example.infograce.databinding.LayersGroupBinding

sealed class RecyclerViewHolders(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    class LayersGroupViewHolder(val binding: LayersGroupBinding): RecyclerViewHolders(binding){
        fun bind(title: RecyclerViewItems.LayersGroup)= with(binding){
            textViewTitle.text = title.titleGroup
            textViewSync.text = title.sync
        }
    }

    class LayersViewHolder(
        val binding: LayerGroupBinding): RecyclerViewHolders(binding){

        private var itemLayer: RecyclerViewItems.Layers? = null
        val layer get() = itemLayer!!

        @SuppressLint("ClickableViewAccessibility")
        fun bind(layer: RecyclerViewItems.Layers) = with(binding){
            itemLayer = layer
            titleView.text = layer.title.title
            transViewNum.text = layer.trans
            elemViewNum.text = layer.elem.toString()
            zoomViewNum.text = layer.zoom
            iconView.setImageResource(layer.icon)
        }
    }

}