package com.example.infograce.dataClass

data class Layers(
    val icon: Int,
    val title: String,
    val trans : String,
    val sync: String,
    val elem: String,
    val zoom: String,
    var visibility: Boolean = false,
    val enable: Boolean = false
)