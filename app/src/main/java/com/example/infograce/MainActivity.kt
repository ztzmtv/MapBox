package com.example.infograce

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infograce.dataClass.DataSource
import com.example.infograce.databinding.MainActivityBinding
import com.example.infograce.recyclerview.RecyclerAdapter
import it.beppi.tristatetogglebutton_library.TriStateToggleButton.ToggleStatus


class MainActivity : AppCompatActivity(), RecyclerAdapter.Listener , SearchView.OnQueryTextListener{

    private lateinit var binding: MainActivityBinding
    private lateinit var adapter: RecyclerAdapter
    var indirectSwitched: Boolean = false

//    private val spanHighlight by lazy {
//        ForegroundColorSpan(
//            ResourcesCompat.getColor(resources, R.color.purple_500, null))
//    }

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
//            it.requestFocus()
        }

        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
//                highlight()
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

//    private fun highlight() {
//        val s = binding.editSearch.text
//        adapter.filteredItems.forEach { item ->
//            // {1}
//            item.title.title.getSpans(0, item.title.title.length, ForegroundColorSpan::class.java).forEach {
//                item.title.title.removeSpan(it)
//            }
//            // {4}
//            if (s?.let { item.title.title.contains(it, true) } == true) {
//                val index = item.title.toString().indexOf(s.toString(), 0, true)
//                item.title.title.setSpan(spanHighlight, index, index + s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            }
//        }
//    }

//    private fun updateSearch() {
//        val s = binding.editSearch.text
//
//        if (s?.length == 0) {
//            // Пользователь очистил поле поиска. Показываем все предметы
//            // Загружаем в адаптер лист со всеми предметами
//            adapter.items = ItemStorage.list
//
//        } else {
//            // Пользователь что-то ввёл. Делаем поиск по этому запросу
//            // Загружаем в адаптер отфильтрованный лист
//            adapter.list = ItemStorage.list.filter {
//                it.abbr.startsWith(s.toString(), true) || it.name.contains(s.toString(), true)
//            } as ArrayList
//        }
//        adapter.notifyDataSetChanged()
//    }
}
