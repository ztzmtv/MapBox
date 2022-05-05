package com.example.infograce

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.dataClass.AddLayer
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.FragmentMainBinding
import com.example.infograce.recyclerview.GestureCallbacks
import com.example.infograce.recyclerview.ItemTouchHelperCallback
import com.example.infograce.recyclerview.RecyclerAdapter
import com.rm.rmswitch.RMTristateSwitch


class MainFragment : Fragment(), RecyclerAdapter.Listener , SearchView.OnQueryTextListener,
    GestureCallbacks {

    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: RecyclerAdapter
    private val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(this))
    private var indirectSwitched: Boolean = false
    private var searchState: Boolean = false
    private var dragState: Boolean = false
    private var lastInput: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(layoutInflater)

        adapter = RecyclerAdapter(this,this)

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        addDataSet()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageAdd.setOnClickListener{
            addLayer()
        }
        binding.imageDelete.setOnClickListener {
            adapter.removeLayer()
        }

        binding.commonSwitchParent.setOnClickListener {
            binding.commonSwitch.performClick()
        }

        binding.imageDrag.setOnClickListener {
            dragState =! dragState
            binding.imageDrag.isSelected = dragState
            adapter.isDraggable =! adapter.isDraggable
            if(dragState) {
                itemTouchHelper.attachToRecyclerView(binding.recyclerView)

            }else itemTouchHelper.attachToRecyclerView(null)
            binding.commonSwitchParent.visibility = if(dragState) View.GONE else View.VISIBLE
            adapter.notifyDataSetChanged()
            adapter.closeLayers()
            val scale = resources.displayMetrics.density
            if (dragState) {
                it.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    marginEnd = 0
                }
                it.setPadding((20 * scale).toInt(), (14 * scale).toInt(), (20 * scale).toInt(), (14 * scale).toInt())
            }
            else{
                it.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    marginEnd = (2*scale).toInt()
                }
                it.setPadding((12 * scale).toInt(), (14 * scale).toInt(), (12 * scale).toInt(), (14 * scale).toInt())
            }
        }


        fun switchFun(switchView: RMTristateSwitch, isSwitchedAny1: Boolean, isSwitchedAll1: Boolean){
            when (switchView.state) {
                RMTristateSwitch.STATE_LEFT -> adapter.switchedOffAll()
                RMTristateSwitch.STATE_MIDDLE -> {
                    if(isSwitchedAny1 && !isSwitchedAll1) {
                        adapter.switchedMidAll()
                    } else {
                        switchView.state =  RMTristateSwitch.STATE_RIGHT
                        switchFun(switchView, isSwitchedAny1, !isSwitchedAll1)
                    }
                }
                RMTristateSwitch.STATE_RIGHT -> adapter.switchedOnAll()
            }
            adapter.notifyDataSetChanged()
        }

        binding.commonSwitch.addSwitchObserver { switchView, _ ->
            val isSwitchedAny1: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.switchSave }
            val isSwitchedAll1: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().all { it.switchSave }
            if (!indirectSwitched) {
                switchFun(switchView, isSwitchedAny1, isSwitchedAll1)
            }
        }

        fun showSoftKeyboard(view: View) {
            if (view.requestFocus()) {
                val imm: InputMethodManager =
                    context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        fun hideSoftKeyboard(view: View) {
            val imm =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        binding.imageSearch.setOnClickListener {
            adapter.closeLayers()
            searchState =! searchState
            if(!searchState){
                lastInput = binding.editSearch.text.toString()
                binding.editSearch.text?.clear()
                hideSoftKeyboard(binding.editSearch)
            }
            else{
                binding.editSearch.setText(lastInput)
                binding.editSearch.text?.let { it1 -> binding.editSearch.setSelection(it1.length) }
                showSoftKeyboard(binding.editSearch)
            }
            binding.imageSearch.isSelected = searchState
            binding.expandableSearch.visibility = if(binding.expandableSearch.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            binding.recyclerView.scrollToPosition(0)
        }

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun addDataSet(){
            val data = DataSource.createDataSet()
            adapter.submitList(data)
        }

        private fun addLayer(){
            val layer = AddLayer.createLayer()
            adapter.addLayer(layer)
        }



        override fun onSwitched() {
            indirectSwitched = true
            val isSwitchedAll: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().all { it.switch }
            val isSwitchedAny: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.switch }

            when(binding.commonSwitch.state) {
                RMTristateSwitch.STATE_LEFT -> adapter.resetSwitchSaveAll()
                RMTristateSwitch.STATE_RIGHT -> adapter.switchSaveToSwitch()
                RMTristateSwitch.STATE_MIDDLE -> {}
            }

            if (isSwitchedAll) {
                binding.commonSwitch.state = RMTristateSwitch.STATE_RIGHT
            }
            if (isSwitchedAny && !isSwitchedAll) {
                binding.commonSwitch.state = RMTristateSwitch.STATE_MIDDLE
            }
            if (!isSwitchedAny) {
                binding.commonSwitch.state = RMTristateSwitch.STATE_LEFT
            }
            indirectSwitched = false

        }

        override fun onScroll(){
            binding.recyclerView.scrollToPosition(adapter.itemCount - 1)
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            adapter.filter.filter(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            adapter.filter.filter(newText)
            return false
        }

        override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }

        override fun onItemMoved(fromPosition: Int, toPosition: Int): Boolean {
            return adapter.onItemMoved(fromPosition, toPosition)
        }

}