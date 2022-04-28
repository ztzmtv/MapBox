package com.example.infograce.recyclerview

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.infograce.MainActivity
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayerGroupBinding
import com.example.infograce.databinding.LayersGroupBinding

sealed class RecyclerViewHolders(binding: ViewBinding, listenerActivity: MainActivity): RecyclerView.ViewHolder(binding.root) {

    class LayersGroupViewHolder(val binding: LayersGroupBinding,private val listenerActivity: MainActivity): RecyclerViewHolders(binding, listenerActivity){
        fun bind(title: RecyclerViewItems.LayersGroup, listenerActivity: MainActivity)= with(binding){
            textViewTitle.text = title.titleGroup
            textViewSync.text = title.sync
        }
    }

    class LayersViewHolder(
        val binding: LayerGroupBinding, private val listenerActivity: MainActivity): RecyclerViewHolders(binding,listenerActivity){

        private var itemLayer: RecyclerViewItems.Layers? = null
        val layer get() = itemLayer!!

        @SuppressLint("ClickableViewAccessibility")
        fun bind(layer: RecyclerViewItems.Layers, listenerActivity: MainActivity, gestureCallbacks: GestureCallbacks) = with(binding){
            itemLayer = layer
            titleView.text = layer.title.title
            transViewNum.text = layer.trans
            elemViewNum.text = layer.elem.toString()
            zoomViewNum.text = layer.zoom
            iconView.setImageResource(layer.icon)



        }
    }

}