package com.example.infograce.recyclerview

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.dataClass.RecyclerViewItems

private const val NO_MOVEMENT = 0

class ItemTouchHelperCallback(
    private val gestureCallbacks: GestureCallbacks
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val canDrag = viewHolder is RecyclerViewHolders.LayersViewHolder
        return if (canDrag) {
            makeMovementFlags(ItemTouchHelper.DOWN or ItemTouchHelper.UP, NO_MOVEMENT)
        } else {
            NO_MOVEMENT
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return gestureCallbacks.onItemMoved(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
    }

    override fun canDropOver(
        recyclerView: RecyclerView,
        current: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return current is RecyclerViewHolders.LayersViewHolder
                && target is RecyclerViewHolders.LayersViewHolder
                && current.layer.group == target.layer.group
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val previousViewHolder = recyclerView.findViewHolderForAdapterPosition(viewHolder.bindingAdapterPosition - 1)
        val nextViewHolder = recyclerView.findViewHolderForAdapterPosition(viewHolder.bindingAdapterPosition + 1)
        val isDraggingUpward = dY < 0
        val isDraggingDownward = dY > 0

        val isDraggingIntoUndraggableArea =
            (isDraggingUpward && previousViewHolder != null && !canDropOver(recyclerView, viewHolder, previousViewHolder))
                    || (isDraggingDownward && nextViewHolder != null && !canDropOver(recyclerView, viewHolder, nextViewHolder))

        val newDy = if (isDraggingIntoUndraggableArea) {
            0f  // Clamp
        } else {
            dY
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, newDy, actionState, isCurrentlyActive)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = Unit

    override fun isItemViewSwipeEnabled() = false
}