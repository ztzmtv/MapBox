package com.example.infograce.recyclerview


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.text.clearSpans
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.MainActivity
import com.example.infograce.R
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.Group
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayerGroupBinding
import com.example.infograce.databinding.LayersGroupBinding
import java.util.*
import kotlin.collections.ArrayList


class RecyclerAdapter(private val listenerActivity: MainActivity, private val gestureCallbacks: GestureCallbacks): RecyclerView.Adapter<RecyclerViewHolders>(), Filterable{


    var items: MutableList<RecyclerViewItems> = ArrayList()
    var filteredItems: MutableList<RecyclerViewItems> = ArrayList()
//    var isSearchable: Boolean = false
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
            is RecyclerViewHolders.LayersGroupViewHolder -> {
                holder.bind(
                    filteredItems[position] as RecyclerViewItems.LayersGroup,
                    listenerActivity
                )
                if (filteredItems[position] == filteredItems[0])
                    holder.binding.textViewTitle.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        setMargins(0, 50, 0, 0)
                    }
//                holder.bind(
//                    items[position] as RecyclerViewItems.LayersGroup,
//                    listenerActivity
//                )

            }
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

//                    Log.d("tagg","change ${filteredItems.map{it::class.simpleName}}")


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


//                    if (holder.binding.titleView.lineCount > 2) holder.binding.titleView.te
                    holder.binding.switch2.setOnClickListener {
                        listenerActivity.onSwitched()
                        currentItem.switchSave = holder.binding.switch2.isChecked
                        Log.d("taggs","${filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map{it.switchSave}}")

                    }
                    holder.binding.switch2.setOnCheckedChangeListener { buttonView, isChecked ->
                        currentItem.switch = isChecked
                    }

                    holder.binding.dragView.setOnTouchListener { _, event ->
                        if (event.action == MotionEvent.ACTION_DOWN) {
                            gestureCallbacks.onStartDrag(holder)
                        }
                        false
                    }



                    holder.binding.switch2.isChecked = currentItem.switch


                    holder.binding.titleLine.setOnClickListener {
                        if (!isDraggable) {
                            if (currentItem.visibility) {
                                currentItem.visibility = false
                            } else {
                                if (currentItem == filteredItems.last()) {
                                    listenerActivity.onScroll()
                                    Log.d("taggg", "${filteredItems.last()}")
                                }
                                closeLayers()
                                currentItem.visibility = true
                            }
                            notifyItemChanged(position)
                        }
                    }

                    holder.binding.titleLine.setOnLongClickListener() {
                        currentItem.enable = !currentItem.enable
                        notifyItemChanged(position)
//                        if (filteredItems[position] == filteredItems.last()) {
//                            listenerActivity.onScroll()
//                        }
                        true
                    }

                    holder.binding.slider.addOnChangeListener { slider, value, fromUser ->
                        holder.binding.transViewNum.setText("${value.toInt()}%")
                    }
                    if (filteredItems[position] == filteredItems.last())
                        holder.binding.expandable.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            setMargins(0, 0, 0, 50)
                        }
                    else{
                        holder.binding.expandable.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                            setMargins(0, 0, 0, 0)
                        }
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
            return when (filteredItems[position]) {
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
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = false }
    }
    fun switchedOnAll(){
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = true }
    }
    fun switchedMidAll(){
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switch = it.switchSave }
    }
    fun resetSwitchSaveAll(){
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switchSave = false }
    }
    fun switchSaveToSwitch(){
        filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map { it.switchSave = it.switch}
    }

    fun closeLayers() {
        filteredItems.mapIndexed { index, t ->
            if(t is RecyclerViewItems.Layers && t.visibility) {
                t.visibility = false
                notifyItemChanged(index)
            }
        }
    }

//    fun setChanged() {
//        filteredItems.mapIndexed { index, t ->
//                notifyItemChanged(index)
//        }
//    }

        fun addLayer(layer: RecyclerViewItems.Layers) {
            filteredItems.add(layer)
            notifyItemChanged(itemCount)
//            Log.d("taggg","${filteredItems.map{it::class.simpleName}}")

        }

        fun removeLayer() {
            filteredItems.removeLast()
            notifyItemChanged(itemCount)
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
            fun onScroll()
        }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                queryText = charString
                    val filteredList = ArrayList<RecyclerViewItems>()
                items.filterIsInstance<RecyclerViewItems.Layers>().filter {
                                (it.title.title.toString().lowercase()
                                    .contains(constraint.toString().lowercase()))
                        }
                        .forEach { filteredList.add(it) }
                Log.d("tagg","items bef bef ${filteredItems.map{it::class.simpleName}}")

                filteredItems = items.filterIndexed { index, recyclerViewItems ->
                     recyclerViewItems in filteredList || recyclerViewItems is RecyclerViewItems.LayersGroup
                }.toMutableList()
                if(filteredItems.last() is RecyclerViewItems.LayersGroup ) filteredItems.removeLast()
                filteredItems = filteredItems.filter { it is RecyclerViewItems.Layers || filteredItems[filteredItems.indexOf(it)+1] !is RecyclerViewItems.LayersGroup}.toMutableList()
                return FilterResults().apply { values = if (charString.isEmpty()) items else filteredItems}
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                Log.d("tagg","publish ${results?.values}")
                filteredItems = if (results?.values == null){
                        ArrayList()
                } else
                    results.values as ArrayList<RecyclerViewItems>
                notifyDataSetChanged()
            }
        }
    }

    fun onItemMoved(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(filteredItems, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(filteredItems, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        notifyItemChanged(fromPosition, toPosition)

        Log.d("tagg","moved ${filteredItems.filterIsInstance<RecyclerViewItems.Layers>().map{it.title.title.length}}")
        return true
    }

}