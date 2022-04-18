package com.example.infograce.dataClass

import android.text.Spannable
import android.text.SpannableString

sealed class RecyclerViewItems {

    data class Layers(
        val icon: Int,
        val title: TitleSpannable,
        val trans : String,
        val sync: String,
        val elem: String,
        val zoom: String,
        var visibility: Boolean = false,
        var enable: Boolean = true,
        var draggable: Boolean = false,
        var switch: Boolean = false,
        var switchSave: Boolean = false
    ):RecyclerViewItems()

    data class TitleSpannable (
        val title: Spannable
    ):RecyclerViewItems() {
        constructor(title: String) : this(SpannableString(title))
    }

    data class LayersGroup(
        val titleGroup: String,
    ):RecyclerViewItems()

}