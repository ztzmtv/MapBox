package com.example.infograce.mapStyleModels

import androidx.annotation.ColorInt

data class TilesetLayer(
    val layerId: String,
    val sourceLayer: String,
    @ColorInt val fillColor: Int,
    @ColorInt val fillOutlineColor: Int,
    val fillOpacity: Double,
    val source: TilesetSource,
)