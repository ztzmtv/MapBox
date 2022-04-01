package com.example.infograce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infograce.dataClass.DataSource
import com.example.infograce.databinding.MainActivityBinding
import com.example.infograce.recyclerview.RecyclerAdapter
import com.example.infograce.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerAdapter()

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
            }
        }

        adapter.touchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        adapter.submitList(data)
    }


}