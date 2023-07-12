package com.villadevs.earthquakemonitor.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "earthquakes")
data class Earthquake(
    @PrimaryKey val id: String,
    val place: String,
    val magnitude: Double,
    val time: Long,
    val longitude: Double,
    val latitude: Double,
) : Parcelable