package com.example.infograce.dataClass

import com.example.infograce.R

class AddLayer {

companion object {
    fun createLayer(): RecyclerViewItems.Layers {
        return RecyclerViewItems.Layers(
            R.drawable.polygon,
            RecyclerViewItems.TitleSpannable("Маска облачности от 12.01.2022"),
            "60%",
            241,
            "16-18",
            Group.RED
        )
    }
}
}

