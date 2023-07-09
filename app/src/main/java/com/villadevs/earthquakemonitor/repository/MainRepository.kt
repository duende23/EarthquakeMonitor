package com.villadevs.earthquakemonitor.repository

import com.villadevs.earthquakemonitor.model.Earthquake
import com.villadevs.earthquakemonitor.model.EarthquakeJsonResponses
import com.villadevs.earthquakemonitor.network.EarthquakeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository {

    suspend fun getEarthquakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            val eartquakeJsonResponse = EarthquakeApi.retrofitService.getLastHourEarthquakes()
            val earthquakeList = parseResult(eartquakeJsonResponse)
            earthquakeList
        }
    }

    private fun parseResult(eartquakeJsonResponse: EarthquakeJsonResponses): MutableList<Earthquake> {
        val earthquakeList = mutableListOf<Earthquake>()
        val featureList = eartquakeJsonResponse.features

        for (feature in featureList) {
            val properties = feature.properties

            val id = feature.id
            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry
            val longitude = geometry.coordinates[0]
            val latitude = geometry.coordinates[1]

            earthquakeList.add(Earthquake(id, place, magnitude,time, longitude, latitude))
        }

        return earthquakeList

    }


}