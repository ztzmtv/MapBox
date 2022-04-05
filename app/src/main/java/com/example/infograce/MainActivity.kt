package com.example.infograce

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infograce.dataClass.DataSource
import com.example.infograce.databinding.MainActivityBinding
import com.example.infograce.recyclerview.RecyclerAdapter
import it.beppi.tristatetogglebutton_library.TriStateToggleButton.ToggleStatus


class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener , SearchView.OnQueryTextListener{

    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: RecyclerAdapter
    var indirectSwitched: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerAdapter(this)

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
            adapter.items.forEachIndexed(){index, value ->
                value.draggable =! value.draggable
                adapter.notifyItemChanged(index)
                adapter.touchHelper.attachToRecyclerView(binding.recyclerView)
            }
            binding.switchBottom.visibility = if(adapter.items.any { it.draggable }) View.GONE else View.VISIBLE
        }

        binding.switchBottom.setOnToggleChanged { toggleStatus, _, _ ->
            if (!indirectSwitched) {
                when (binding.switchBottom.toggleStatus) {
                    ToggleStatus.off -> adapter.switchedOffAll()
                    ToggleStatus.mid -> adapter.switchedMidAll()
                    ToggleStatus.on -> adapter.switchedOnAll()
                    null -> {}
                }
                adapter.notifyDataSetChanged()
            }
        }

        binding.imageSearch.setOnClickListener {
            binding.expandableSearch.visibility = if(binding.expandableSearch.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

    }

    private fun addDataSet(search: String){
        val data = DataSource.createDataSet(search)
        adapter.submitList(data)
    }

    override fun onSwitched() {
        indirectSwitched = true
        val isSwitchedAll: Boolean = adapter.items.all { it.switch }
        val isSwitchedAny: Boolean = adapter.items.any { it.switch }

            if (isSwitchedAll) {
                binding.switchBottom.toggleOn()
            }
            if (isSwitchedAny && !isSwitchedAll) {
                binding.switchBottom.toggleMid()
            }
            if (!isSwitchedAny) {
                binding.switchBottom.toggleOff()
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
}
