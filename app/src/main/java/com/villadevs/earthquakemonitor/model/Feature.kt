package com.villadevs.earthquakemonitor.model

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,

)