package com.example.infograce.recyclerview


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.text.clearSpans
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.MainActivity
import com.example.infograce.R
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayerGroupBinding
import com.example.infograce.databinding.LayersGroupBinding
import java.util.*




class RecyclerAdapter(private val listenerActivity: MainActivity, private val gestureCallbacks: GestureCallbacks): RecyclerView.Adapter<RecyclerViewHolders>(), Filterable{


    var items: MutableList<RecyclerViewItems> = ArrayList()
    var filteredItems: MutableList<RecyclerViewItems> = ArrayList()
    var isDraggable: Boolean = false
    var isVisible: Boolean = false
    var queryText=""


    private val spanHighlight by lazy {
        BackgroundColorSpan(
            Color.parseColor("#59BD87"))
    }


//    override fun getItemViewType(position: Int): Int {
//        return if (items[position] is RecyclerViewItems.LayersGroup) {
//            R.layout.layers_group
//        } else {
//            R.layout.layer_group
//        }
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
                if (currentItem is RecyclerViewItems.Layers) {
                    holder.bind(currentItem as RecyclerViewItems.Layers, listenerActivity, gestureCallbacks)
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
                    holder.bind(currentItem, listenerActivity, gestureCallbacks)

                    isVisible = currentItem.visibility
                    holder.binding.expandable.visibility =
                        if (isVisible) View.VISIBLE else View.GONE

                    if (isVisible) {
                        holder.binding.titleView.setTypeface(null, Typeface.BOLD)
                        holder.binding.titleView.setTextColor(Color.parseColor("#59BD87"))
                        holder.binding.iconView.setColorFilter(Color.parseColor("#59BD87"))
                        holder.binding.chevron.setImageResource(R.drawable.chevron_reverse)
                    } else {
                        holder.binding.titleView.setTypeface(null, Typeface.NORMAL)
                        holder.binding.titleView.setTextColor(Color.WHITE)
                        holder.binding.iconView.setColorFilter(Color.WHITE)
                        holder.binding.chevron.setImageResource(R.drawable.chevron_right)
                    }

                    val isEnable: Boolean = currentItem.enable
                    holder.binding.titleLine.alpha = if (isEnable) 1f else 0.5f
                    holder.binding.invisView.visibility = if (isEnable) View.GONE else View.VISIBLE

                    holder.binding.switch2.visibility =
                        if (isDraggable) View.INVISIBLE else View.VISIBLE
                    holder.binding.dragView.visibility =
                        if (isDraggable) View.VISIBLE else View.GONE

                    holder.binding.switch2.isChecked = currentItem.switch

                    holder.binding.titleLine.setOnClickListener {
                        if(currentItem.visibility){
                            currentItem.visibility = false
                        }else{
                            closeLayers()
                            currentItem.visibility = true
                        }
                        notifyItemChanged(position)
                    }

                    holder.binding.titleLine.setOnLongClickListener() {
                        currentItem.enable = !currentItem.enable
                        notifyItemChanged(position)
                        true
                    }

                    holder.binding.slider.addOnChangeListener { slider, value, fromUser ->
                        holder.binding.transView.setText("Прозрачность: ${value.toInt()}%")
                    }

//                    holder.binding.dragView.setOnTouchListener { v, event ->
//                        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
//                            listenerActivity.itemTouchHelper.startDrag(holder)
//                        }
//                        false
//                    }

                }
            }
        }
    }



        override fun getItemCount(): Int {
            return filteredItems.size
        }

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
    fun resetSwitchSaveAll(){
        items.filterIsInstance<RecyclerViewItems.Layers>().map { it.switchSave = false }
    }
    fun switchSaveToSwitch(){
        items.filterIsInstance<RecyclerViewItems.Layers>().map { it.switchSave = it.switch}
    }

    private fun closeLayers() {
        filteredItems.mapIndexed { index, t ->
            if(t is RecyclerViewItems.Layers && t.visibility) {
                t.visibility = false
                notifyItemChanged(index)
            }
        }
    }

    fun setChanged() {
        filteredItems.mapIndexed { index, t ->
                notifyItemChanged(index)
        }
    }

        fun addLayer() {
            items.add(DataSource.addLayer[0])
            notifyItemChanged(items.size)
        }

        fun removeLayer() {
            items.removeLast()
            notifyItemChanged(items.size)
        }

//        val touchHelper =
//            ItemTouchHelper(object :
//                ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
//                override fun onMove(
//                    p0: RecyclerView,
//                    p1: RecyclerView.ViewHolder,
//                    p2: RecyclerView.ViewHolder
//                ): Boolean {
//                    val sourcePosition = p1.adapterPosition
//                    val targetPosition = p2.adapterPosition
//                    Collections.swap(filteredItems, sourcePosition, targetPosition)
//                    notifyItemMoved(sourcePosition, targetPosition)
//                    return true
//                }
//                override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
//                }
//                override fun isLongPressDragEnabled(): Boolean {
//                    return false
//                }
//            })

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
                Log.d("tagg","publish ${results?.values}")
                filteredItems = if (results?.values == null)
                    ArrayList()
                else
//                    results.values as MutableList<RecyclerViewItems>
                    results.values as ArrayList<RecyclerViewItems>
                notifyDataSetChanged()
            }
        }
    }

    fun onItemMoved(fromPosition: Int, toPosition: Int): Boolean {
        // This is what does the neat drag swapping animation.
        // Source: https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

}