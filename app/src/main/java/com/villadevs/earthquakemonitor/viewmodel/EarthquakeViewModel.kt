package com.villadevs.earthquakemonitor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.villadevs.earthquakemonitor.model.Earthquake
import com.villadevs.earthquakemonitor.model.EarthquakeJsonResponses
import com.villadevs.earthquakemonitor.network.EarthquakeApi
import com.villadevs.earthquakemonitor.network.EarthquakeApiService
import com.villadevs.earthquakemonitor.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EarthquakeViewModel : ViewModel() {

    private val _earthqueakes = MutableLiveData<List<Earthquake>>()
    val earthqueakes: LiveData<List<Earthquake>>
        get() = _earthqueakes

    private val repository = MainRepository()

    init {
        //getEarthquakes()
        viewModelScope.launch {
            _earthqueakes.value = repository.getEarthquakes()
        }

    }

    /*private fun getEarthquakes() {
        val eartquakeList = mutableListOf<Earthquake>()
        eartquakeList.add(Earthquake("1", "Madrid", 8.9, 1100, 37.8, 38.4))
        eartquakeList.add(Earthquake("2", "Londres", 4.6, 300, 67.8, 39.4))
        eartquakeList.add(Earthquake("3", "Buenos Aires", 3.5, 300, 67.8, 35.4))
        eartquakeList.add(Earthquake("4", "Berlín", 6.9, 3900, 37.8, 25.4))
        eartquakeList.add(Earthquake("5", "Portugal", 5.6, 6400, 17.8, 55.4))
        eartquakeList.add(Earthquake("6", "Francia", 3.9, 5400, 27.8, 35.4))

        _earthqueakes.value = eartquakeList
    }*/

}