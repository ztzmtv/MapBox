package com.example.infograce.dataClass

import android.text.Spannable
import android.text.SpannableString

data class Layers(
    val icon: Int,
    val title: titleSpannable,
    val trans : String,
    val sync: String,
    val elem: String,
    val zoom: String,
    var visibility: Boolean = false,
    var enable: Boolean = true,
    var draggable: Boolean = false,
    var switch: Boolean = false,
    var switchSave: Boolean = false
){
//    constructor(title: Spannable,

//                visibility: Boolean = false,
//                enable: Boolean = true,
//                draggable: Boolean = false,
//                switch: Boolean = false,
//                switchSave: Boolean = false
//    ) : this(SpannableString(title),SpannableString(icon),SpannableString(trans),SpannableString(sync),SpannableString(elem),SpannableString(zoom))
}

data class titleSpannable (
    val title:  Spannable) {
    constructor(title: String) : this(SpannableString(title))
}