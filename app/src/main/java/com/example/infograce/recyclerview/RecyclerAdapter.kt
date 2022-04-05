package com.example.infograce.recyclerview

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.R
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.Layers
import com.example.infograce.databinding.LayerGroupBinding
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(val listener: Listener): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), Filterable{

    var items: MutableList<Layers> = ArrayList()
    var filteredItems: MutableList<Layers> = ArrayList()

    class ViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val binding = LayerGroupBinding.bind(itemView)
        fun bind(layers: Layers, listener: Listener) = with(binding){
            titleView.text = layers.title
            transView.text = layers.trans
            syncView.text = layers.sync
            elemView.text = layers.elem
            zoomView.text = layers.zoom
            iconView.setImageResource(layers.icon)

            switch2.setOnClickListener {
                layers.switchSave = switch2.isChecked
                listener.onSwitched()
            }
                switch2.setOnCheckedChangeListener { buttonView, isChecked ->
                    layers.switch = isChecked
                }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.bind(currentItem, listener)

        val isVisible: Boolean = currentItem.visibility
        holder.binding.expandable.visibility = if (isVisible) View.VISIBLE else View.GONE

        if (isVisible) {
            holder.binding.titleView.setTypeface(null, Typeface.BOLD)
            holder.binding.titleView.setTextColor(Color.parseColor("#59BD87"))
            holder.binding.iconView.setColorFilter(Color.parseColor("#59BD87"))
        }else{
            holder.binding.titleView.setTypeface(null, Typeface.NORMAL)
            holder.binding.titleView.setTextColor(Color.WHITE)
            holder.binding.iconView.setColorFilter(Color.WHITE)
        }

        val isEnable: Boolean = currentItem.enable
        holder.binding.titleLine.alpha = if (isEnable) 1f else 0.5f
        holder.binding.invisView.visibility = if (isEnable) View.GONE else View.VISIBLE

        val isDraggable: Boolean = currentItem.draggable
        holder.binding.switch2.visibility = if (isDraggable) View.INVISIBLE else View.VISIBLE
        holder.binding.dragView.visibility = if (isDraggable) View.VISIBLE else View.GONE

        holder.binding.switch2.isChecked = currentItem.switch

        holder.binding.chevron.setOnClickListener{
            currentItem.visibility =! currentItem.visibility
            notifyItemChanged(position)
        }

        holder.binding.slider.addOnChangeListener { slider, value, fromUser ->
            holder.binding.transView.setText("Прозрачность: ${value.toInt()}%")
        }

        holder.binding.titleLine.setOnLongClickListener(){
            currentItem.enable = !currentItem.enable
            notifyItemChanged(position)
            true
        }

        holder.binding.dragView.setOnTouchListener { v, event ->
            if(event.actionMasked== MotionEvent.ACTION_DOWN){
                touchHelper.startDrag(holder)
            }
            false
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

    fun switchedOffAll(){
        items.map { it.switch = false }
    }
    fun switchedOnAll(){
        items.map { it.switch = true }
    }
    fun switchedMidAll(){
        items.map { it.switch = it.switchSave }
    }

    fun addLayer(){
        items.add(DataSource.addLayer[0])
        notifyItemChanged(itemCount)
    }

    fun removeLayer(){
        items.removeLast()
        notifyItemChanged(itemCount)
    }

    val touchHelper =
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
        override fun onMove(
            p0: RecyclerView,
            p1: RecyclerView.ViewHolder,
            p2: RecyclerView.ViewHolder
        ): Boolean {
            val sourcePosition = p1.adapterPosition
            val targetPosition = p2.adapterPosition
            Collections.swap(items,sourcePosition,targetPosition)
            notifyItemMoved(sourcePosition,targetPosition)
            return true
        }
        override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        }
    })

    interface Listener{
        fun onSwitched()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filteredItems = items else {
                    val filteredList = ArrayList<Layers>()
                    items
                        .filter {
                            (it.title.contains(constraint!!)) or
                                    (it.title.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    filteredItems = filteredList

                }
                return FilterResults().apply { values = filteredItems }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                filteredItems = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Layers>
                notifyDataSetChanged()
            }
        }
    }

}