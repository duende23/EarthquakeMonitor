package com.villadevs.earthquakemonitor.repository

import android.webkit.WebChromeClient.FileChooserParams.parseResult
import com.villadevs.earthquakemonitor.database.EarthquakeDao
import com.villadevs.earthquakemonitor.model.Earthquake
import com.villadevs.earthquakemonitor.model.EarthquakeJsonResponses
import com.villadevs.earthquakemonitor.network.EarthquakeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val earthquakeDao: EarthquakeDao) {

    suspend fun getEarthquakes(sortByMagnitude:Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            val eartquakeJsonResponse = EarthquakeApi.retrofitService.getLastHourEarthquakes()
            //Extaemos datos de internet
            val earthquakeList = parseResult(eartquakeJsonResponse)

            //Insertamos los datos descargados de internet en nuestra base de datos
            earthquakeDao.insertAllEarthquakes(earthquakeList)

            getEarthquakesFromDB(sortByMagnitude)

           /* val earthquakes : MutableList<Earthquake>

            if(sortByMagnitude){
                //Extraemos de la base de datos la lista de terremotos ordenados por magnitude
               earthquakes =earthquakeDao.getEarthquakeByMagnitude()
            }else{
                //Extraemos de la base de datos la lista de terremotos ordenadas por defecto (por hora de suceso)
                earthquakes  = earthquakeDao.getEarthquakes()
            }

            //Extraemos de la base de datos la lista de terremotos
            //val earthquakes = earthquakeDao.getEarthquakes()
            earthquakes
            //earthquakeList*/
        }
    }


    suspend fun getEarthquakesFromDB(sortByMagnitude:Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {
            if (sortByMagnitude) {
                //Extraemos de la base de datos la lista de terremotos ordenados por magnitude
                earthquakeDao.getEarthquakeByMagnitude()
            } else {
                //Extraemos de la base de datos la lista de terremotos ordenadas por defecto (por hora de suceso)
                earthquakeDao.getEarthquakes()
            }
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