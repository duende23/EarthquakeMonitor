package com.villadevs.earthquakemonitor

import android.app.Application
import com.villadevs.earthquakemonitor.database.EarthquakeDatabase

class EarthquakeApplication:Application (){
    val database: EarthquakeDatabase by lazy { EarthquakeDatabase.getDatabase(this) }
}