package com.example.infograce.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.databinding.LayerGroupBinding

interface GestureCallbacks {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    fun onItemMoved(fromPosition: Int, toPosition: Int): Boolean
}