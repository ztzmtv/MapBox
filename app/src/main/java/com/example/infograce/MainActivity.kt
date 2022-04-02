package com.example.infograce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.infograce.dataClass.DataSource
import com.example.infograce.dataClass.Layers
import com.example.infograce.databinding.MainActivityBinding
import com.example.infograce.recyclerview.RecyclerAdapter
import com.example.infograce.ui.main.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener{

    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerAdapter(this)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        addDataSet()

        binding.imageAdd.setOnClickListener{
            adapter.addLayer()
        }

        binding.imageDelete.setOnClickListener {
            adapter.removeLayer()
        }

        binding.imageDrag.setOnClickListener{
            adapter.items.forEachIndexed(){index, value ->
                value.draggable =! value.draggable
                adapter.notifyItemChanged(index)
                adapter.touchHelper.attachToRecyclerView(binding.recyclerView)
            }
        }
        binding.switchBottom.setOnCheckedChangeListener{ buttonView, isChecked ->
            adapter.items.forEachIndexed(){index, value ->
                value.switch = isChecked
//                value.switchBottom = isChecked
                adapter.notifyItemChanged(index)
            }
        }
    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        adapter.submitList(data)
    }

    override fun onSwitched() {
        val isSwitchedAll: Boolean = adapter.items.all { it.switch }
        val isSwitchedAny: Boolean = adapter.items.any { it.switch }
        Toast.makeText(this,"all $isSwitchedAll, any $isSwitchedAny", Toast.LENGTH_SHORT).show()

        CoroutineScope(Dispatchers.Main).launch {
            if (isSwitchedAll) {
                binding.switchBottom.isChecked = true
                binding.switchBottom.alpha = 1f
            }
            if (isSwitchedAny && !isSwitchedAll) binding.switchBottom.alpha = 0.5f
            if (!isSwitchedAny) {
                binding.switchBottom.isChecked = false
                binding.switchBottom.alpha = 1f
            }
        }

    }

}
