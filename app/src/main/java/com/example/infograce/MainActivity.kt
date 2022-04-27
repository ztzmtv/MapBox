package com.example.infograce

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.RecyclerViewItems
import com.example.infograce.databinding.LayerGroupBinding
import com.example.infograce.databinding.MainActivityBinding
import com.example.infograce.recyclerview.GestureCallbacks
import com.example.infograce.recyclerview.ItemTouchHelperCallback
import com.example.infograce.recyclerview.RecyclerAdapter
import com.rm.rmswitch.RMTristateSwitch
import com.rm.rmswitch.RMTristateSwitch.RMTristateSwitchObserver


class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener , SearchView.OnQueryTextListener,
    GestureCallbacks {

    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: RecyclerAdapter
    private val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(this))
    private var indirectSwitched: Boolean = false
    var searchState: Boolean = false
    var dragState: Boolean = false
    var lastInput: String = ""

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerAdapter(this,this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        addDataSet("")

        binding.imageAdd.setOnClickListener{
            adapter.addLayer()
        }
        binding.imageDelete.setOnClickListener {
            adapter.removeLayer()
        }

        binding.imageDrag.setOnClickListener {
            dragState =! dragState
            binding.imageDrag.isSelected = dragState
            adapter.isDraggable =! adapter.isDraggable
            if(dragState) itemTouchHelper.attachToRecyclerView(binding.recyclerView) else itemTouchHelper.attachToRecyclerView(null)
            binding.commonSwitch.visibility = if(dragState) View.GONE else View.VISIBLE
            adapter.notifyDataSetChanged()
//            Log.d("taggg","\n ${adapter.items.size} ${adapter.items.map{it::class.simpleName}}\n${adapter.filteredItems.size} ${adapter.filteredItems.map{it::class.simpleName}}")

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

        binding.commonSwitch.addSwitchObserver(RMTristateSwitchObserver { switchView, state ->
            val isSwitchedAny1: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.switchSave }
            val isSwitchedAll1: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().all { it.switchSave }
            if (!indirectSwitched) {
                switchFun(switchView, isSwitchedAny1, isSwitchedAll1)
            }
        })

        fun showSoftKeyboard(view: View) {
            if (view.requestFocus()) {
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        fun hideSoftKeyboard(view: View) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

        }

        binding.imageSearch.setOnClickListener {
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

    private fun addDataSet(search: String){
        val data = DataSource.createDataSet(search)
        adapter.submitList(data)
    }

    override fun onSwitched() {
        indirectSwitched = true
        val isSwitchedAll: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().all { it.switch }
        val isSwitchedAny: Boolean = adapter.items.filterIsInstance<RecyclerViewItems.Layers>().any { it.switch }

        when(binding.commonSwitch.state) {
            RMTristateSwitch.STATE_LEFT -> adapter.resetSwitchSaveAll()
            RMTristateSwitch.STATE_RIGHT -> adapter.switchSaveToSwitch()
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        Log.d("tagg","start")
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int): Boolean {
        return adapter.onItemMoved(fromPosition, toPosition)
    }

}
