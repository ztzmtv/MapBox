package com.example.infograce.recyclerview

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.infograce.MainActivity
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayerGroupBinding
import com.example.infograce.databinding.LayersGroupBinding

sealed class RecyclerViewHolders(binding: ViewBinding, listenerActivity: MainActivity): RecyclerView.ViewHolder(binding.root) {

    class LayersGroupViewHolder(private val binding: LayersGroupBinding,private val listenerActivity: MainActivity): RecyclerViewHolders(binding, listenerActivity){
        fun bind(title: RecyclerViewItems.LayersGroup, listenerActivity: MainActivity){
            binding.textViewTitle.text = title.titleGroup
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
            transView.text = layer.trans
            syncView.text = layer.sync
            elemView.text = layer.elem
            zoomView.text = layer.zoom
            iconView.setImageResource(layer.icon)

            switch2.setOnClickListener {
                listenerActivity.onSwitched()
                layer.switchSave = switch2.isChecked
            }
            switch2.setOnCheckedChangeListener { buttonView, isChecked ->
                layer.switch = isChecked
            }

            binding.dragView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    gestureCallbacks.onStartDrag(this@LayersViewHolder)
                }
                false
            }
        }
    }

}