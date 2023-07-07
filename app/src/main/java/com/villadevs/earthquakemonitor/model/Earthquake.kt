package com.villadevs.earthquakemonitor.model

data class Earthquake(
    val id: String,
    val place: String,
    val magnitude: Double,
    val time: Long,
    val longitude: Double,
    val latitude: Double,
)