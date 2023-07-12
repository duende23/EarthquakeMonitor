package com.villadevs.earthquakemonitor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.villadevs.earthquakemonitor.database.EarthquakeDao

class EarthquakeViewModelFactory (private val earthquakeDao: EarthquakeDao, private val sortType: Boolean) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EarthquakeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EarthquakeViewModel(earthquakeDao, sortType) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}