package com.villadevs.earthquakemonitor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.villadevs.earthquakemonitor.model.Earthquake

class EarthquakeViewModel : ViewModel() {

    private val _earthqueakes = MutableLiveData<List<Earthquake>>()
    val earthqueakes: LiveData<List<Earthquake>>
        get() = _earthqueakes


    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getEarthquakes()
    }

    private fun getEarthquakes() {

    }

}