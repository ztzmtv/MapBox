package com.example.infograce.recyclerview

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
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
//import com.example.infograce.dataClass.Layers
import com.example.infograce.databinding.LayerGroupBinding
import java.util.*
import kotlin.collections.ArrayList
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.core.text.clearSpans
import com.example.infograce.MainActivity
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayersGroupBinding
import kotlinx.coroutines.joinAll
import java.lang.IllegalArgumentException

//import com.example.infograce.ui.main.MainFragment


class RecyclerAdapter(private val listenerActivity: MainActivity): RecyclerView.Adapter<RecyclerViewHolders>(), Filterable{


    var items: MutableList<RecyclerViewItems> = ArrayList()
    var filteredItems: MutableList<RecyclerViewItems> = ArrayList()
//    var filteredItemsFirst: MutableList<RecyclerViewItems.Layers> = ArrayList()
    var queryText=""

    private val spanHighlight by lazy {
        ForegroundColorSpan(
            Color.parseColor("#FF0027FF"))
    }


//    class ViewHolder constructor(
//        itemView: View
//    ): RecyclerView.ViewHolder(itemView){
//        val binding = LayerGroupBinding.bind(itemView)
//        fun bind(layers: RecyclerViewItems, listenerActivity: MainActivity) = with(binding){
//            titleView.text = layers.title.title
//            transView.text = layers.trans
//            syncView.text = layers.sync
//            elemView.text = layers.elem
//            zoomView.text = layers.zoom
//            iconView.setImageResource(layers.icon)
//
//            switch2.setOnClickListener {
//                layers.switchSave = switch2.isChecked
//                listenerActivity.onSwitched()
//            }
//            switch2.setOnCheckedChangeListener { buttonView, isChecked ->
//                layers.switch = isChecked
//            }
//        }
//
//    }




    @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolders, position: Int) {
        when (holder) {
            is RecyclerViewHolders.LayersGroupViewHolder -> holder.bind(
                items[position] as RecyclerViewItems.LayersGroup,
                listenerActivity
            )
            is RecyclerViewHolders.LayersViewHolder -> {
                val currentItem = filteredItems[position]
                Log.d("taggg","${items.size},${filteredItems.size}")
                if (currentItem is RecyclerViewItems.Layers) {
                    holder.bind(currentItem as RecyclerViewItems.Layers, listenerActivity)
                    if (queryText.isNotEmpty()) {
                        val startPos =
                            currentItem.title.title.toString().lowercase()
                                .indexOf(queryText.lowercase())
                        val endPos = startPos + queryText.length
                        if (startPos != -1) {
                            if (queryText.let { currentItem.title.title.contains(it, true) }) {
                                currentItem.title.title.setSpan(
                                    spanHighlight,
                                    startPos,
                                    endPos,
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                                )
                            }
                        }
                    } else {
                        currentItem.title.title.clearSpans()
                    }
                    holder.bind(currentItem, listenerActivity)

                    val isVisible: Boolean = currentItem.visibility
                    holder.binding.expandable.visibility =
                        if (isVisible) View.VISIBLE else View.GONE

                    if (isVisible) {
                        holder.binding.titleView.setTypeface(null, Typeface.BOLD)
                        holder.binding.titleView.setTextColor(Color.parseColor("#59BD87"))
                        holder.binding.iconView.setColorFilter(Color.parseColor("#59BD87"))
                    } else {
                        holder.binding.titleView.setTypeface(null, Typeface.NORMAL)
                        holder.binding.titleView.setTextColor(Color.WHITE)
                        holder.binding.iconView.setColorFilter(Color.WHITE)
                    }

                    val isEnable: Boolean = currentItem.enable
                    holder.binding.titleLine.alpha = if (isEnable) 1f else 0.5f
                    holder.binding.invisView.visibility = if (isEnable) View.GONE else View.VISIBLE

                    val isDraggable: Boolean = currentItem.draggable
                    holder.binding.switch2.visibility =
                        if (isDraggable) View.INVISIBLE else View.VISIBLE
                    holder.binding.dragView.visibility =
                        if (isDraggable) View.VISIBLE else View.GONE

                    holder.binding.switch2.isChecked = currentItem.switch

                    holder.binding.chevron.setOnClickListener {
                        currentItem.visibility = !currentItem.visibility
                        notifyItemChanged(position)
                    }

                    holder.binding.slider.addOnChangeListener { slider, value, fromUser ->
                        holder.binding.transView.setText("Прозрачность: ${value.toInt()}%")
                    }

                    holder.binding.titleLine.setOnLongClickListener() {
                        currentItem.enable = !currentItem.enable
                        notifyItemChanged(position)
                        true
                    }

                    holder.binding.dragView.setOnTouchListener { v, event ->
                        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                            touchHelper.startDrag(holder)
                        }
                        false
                    }

                }
            }
        }
    }





        override fun getItemCount(): Int {
            return filteredItems.size
        }

        //    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.layer_group, parent,false)
//        return ViewHolder(view)
//    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolders {
            return when (viewType) {
                R.layout.layer_group -> RecyclerViewHolders.LayersViewHolder(
                    LayerGroupBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), listenerActivity
                )
                R.layout.layers_group -> RecyclerViewHolders.LayersGroupViewHolder(
                    LayersGroupBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), listenerActivity
                )
                else -> throw IllegalArgumentException("Invalid ViewType Provided ")
            }
        }


        override fun getItemViewType(position: Int): Int {
            return when (items[position]) {
                is RecyclerViewItems.TitleSpannable -> R.layout.layer_group
                is RecyclerViewItems.LayersGroup -> R.layout.layers_group
                is RecyclerViewItems.Layers -> R.layout.layer_group

            }
        }

        fun submitList(list: MutableList<RecyclerViewItems>) {
            items = list
            filteredItems = list
//            filteredItems = list.toMutableList()
//            filteredItems[list.indexOf(RecyclerViewItems.LayersGroup("Общие слои"))] = null
        }

    fun switchedOffAll(){
        items.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = false }
    }
    fun switchedOnAll(){
        items.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = true }
    }
    fun switchedMidAll(){
        items.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = it.switchSave }
    }

        fun addLayer() {
            items.add(DataSource.addLayer[0])
            notifyItemChanged(itemCount)
        }

        fun removeLayer() {
            items.removeLast()
            notifyItemChanged(itemCount)
        }

        val touchHelper =
            ItemTouchHelper(object :
                ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                override fun onMove(
                    p0: RecyclerView,
                    p1: RecyclerView.ViewHolder,
                    p2: RecyclerView.ViewHolder
                ): Boolean {
                    val sourcePosition = p1.adapterPosition
                    val targetPosition = p2.adapterPosition
                    Collections.swap(items, sourcePosition, targetPosition)
                    notifyItemMoved(sourcePosition, targetPosition)
                    return true
                }
                override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                }
            })

        interface Listener {
            fun onSwitched()
        }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                queryText = charString
                    val filteredList = ArrayList<RecyclerViewItems>()
                    items.filterIsInstance<RecyclerViewItems.Layers>().filter {
                                (it.title.title.toString().lowercase()
                                    .contains(constraint.toString().lowercase()!!)) or
                                        (it.title.title.toString().lowercase()
                                            .contains(constraint.toString().lowercase()))
                        }
                        .forEach { filteredList.add(it) }
//                filteredItems = filteredList.toMutableList()
                filteredItems = filteredList
                Log.d("tagg","return")
                return FilterResults().apply { values = if (charString.isEmpty()) items else filteredItems }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Log.d("tagg","publish $results")
                filteredItems = if (results?.values == null)
                    ArrayList()
                else
//                    results.values as MutableList<RecyclerViewItems>
                    results.values as ArrayList<RecyclerViewItems>
                notifyDataSetChanged()
            }
        }
    }
}