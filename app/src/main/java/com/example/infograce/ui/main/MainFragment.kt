package com.example.infograce.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.infograce.R
import com.example.infograce.databinding.MainFragmentBinding
import com.example.infograce.recyclerview.RecyclerAdapter

class MainFragment : Fragment() {
    // TODO: Implement the Fragment
}
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.infograce.R
//import com.example.infograce.SecViewModel
//
//class MainFragment : Fragment() {
//
//    private lateinit var viewModel: MainViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.main_fragment, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        // TODO: Use the ViewModel
//    }
//
//}



//import androidx.lifecycle.ViewModelProvider
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.widget.SearchView
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.infograce.MainActivity
//import com.example.infograce.dataClass.DataSource
//import com.example.infograce.databinding.MainFragmentBinding
//import com.example.infograce.recyclerview.RecyclerAdapter
//
//class MainFragment : Fragment(),RecyclerAdapter.Listener , SearchView.OnQueryTextListener  {
//
//    private lateinit var viewModel: MainViewModel
//    private var _binding: MainFragmentBinding? = null
//    private lateinit var adapterFargment: RecyclerAdapter
//    var indirectSwitched: Boolean = false
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = MainFragmentBinding.inflate(inflater, container, false)
//        val view = binding.root
//        return view
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//
//        adapterFargment = RecyclerAdapter(this)
//
//        val layoutManager = LinearLayoutManager(activity)
//        binding.recyclerView.layoutManager = layoutManager
//        binding.recyclerView.adapter = adapterFargment
//
//        addDataSet("")
//
////        binding.imageAdd.setOnClickListener{
////            adapter.addLayer()
////        }
////
////        binding.imageDelete.setOnClickListener {
////            adapter.removeLayer()
////        }
////
////        binding.imageDrag.setOnClickListener {
////            adapter.items.forEachIndexed(){index, value ->
////                value.draggable =! value.draggable
////                adapter.notifyItemChanged(index)
////                adapter.touchHelper.attachToRecyclerView(binding.recyclerView)
////            }
////            binding.switchBottom.visibility = if(adapter.items.any { it.draggable }) View.GONE else View.VISIBLE
////        }
////
////        binding.switchBottom.setOnToggleChanged { toggleStatus, _, _ ->
////            if (!indirectSwitched) {
////                when (binding.switchBottom.toggleStatus) {
////                    TriStateToggleButton.ToggleStatus.off -> adapter.switchedOffAll()
////                    TriStateToggleButton.ToggleStatus.mid -> adapter.switchedMidAll()
////                    TriStateToggleButton.ToggleStatus.on -> adapter.switchedOnAll()
////                    null -> {}
////                }
////                adapter.notifyDataSetChanged()
////            }
////        }
////
////        binding.imageSearch.setOnClickListener {
////            binding.expandableSearch.visibility = if(binding.expandableSearch.visibility == View.VISIBLE) View.GONE else View.VISIBLE
////        }
////
////        binding.editSearch.addTextChangedListener(object : TextWatcher {
////            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
////            }
////            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
////                adapter.filter.filter(s)
////            }
////            override fun afterTextChanged(s: Editable?) {
////            }
////        })
////
////        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
////
////            override fun onTabSelected(tab: TabLayout.Tab?) {
////                var position = tab?.position
////                if (position == 1) {
////                    findNavController().navigate(R.id.action_mainFragment_to_secFragment)
////                }
////            }
////
////            override fun onTabUnselected(tab: TabLayout.Tab?) {
////            }
////
////            override fun onTabReselected(tab: TabLayout.Tab?) {
////            }
////        })
//
//
//    }
//
//    private fun addDataSet(search: String){
//        val data = DataSource.createDataSet(search)
//        adapterFargment.submitList(data)
//    }
//
//    override fun onSwitched() {
////        indirectSwitched = true
////        val isSwitchedAll: Boolean = adapter.items.all { it.switch }
////        val isSwitchedAny: Boolean = adapter.items.any { it.switch }
////
////        if (isSwitchedAll) {
////            binding.switchBottom.toggleOn()
////        }
////        if (isSwitchedAny && !isSwitchedAll) {
////            binding.switchBottom.toggleMid()
////        }
////        if (!isSwitchedAny) {
////            binding.switchBottom.toggleOff()
////        }
////        indirectSwitched = false
//    }
//
//    override fun onQueryTextSubmit(query: String?): Boolean {
//        adapterFargment.filter.filter(query)
//        return false
//    }
//
//    override fun onQueryTextChange(newText: String?): Boolean {
//        adapterFargment.filter.filter(newText)
//        return false
//    }
//
//}