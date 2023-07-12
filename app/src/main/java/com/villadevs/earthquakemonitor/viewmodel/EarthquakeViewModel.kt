package com.villadevs.earthquakemonitor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.villadevs.earthquakemonitor.database.EarthquakeDao
import com.villadevs.earthquakemonitor.model.Earthquake
import com.villadevs.earthquakemonitor.network.ApiResponseStatus
import com.villadevs.earthquakemonitor.repository.MainRepository
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class EarthquakeViewModel(private val earthquakeDao: EarthquakeDao, private val sortType: Boolean) : ViewModel() {

    //val earthquakes: LiveData<List<Earthquake>> = earthquakeDao.getEarthquakes().asLiveData()

    private val _earthqueakes = MutableLiveData<List<Earthquake>>()
    val earthqueakes: LiveData<List<Earthquake>>
        get() = _earthqueakes

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
        get() = _status



    private val repository = MainRepository(earthquakeDao)

    init {
        //getEarthquakes()
        //Para que por defecto los ordene por tiempo
        reloadEarthquake(sortType)
    }

    fun reloadEarthquake(sortType: Boolean) {
        try {
            _status.value = ApiResponseStatus.LOADING
            viewModelScope.launch {
                _earthqueakes.value = repository.getEarthquakes(sortType)
                _status.value = ApiResponseStatus.DONE
            }
        } catch (e: UnknownHostException) {
            _status.value = ApiResponseStatus.NOT_INTERNET_CONNECTION
        }
    }

    fun reloadEarthquakeFromDB(sortByMagnitude: Boolean) {
        viewModelScope.launch {
            _earthqueakes.value = repository.getEarthquakesFromDB(sortByMagnitude)
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