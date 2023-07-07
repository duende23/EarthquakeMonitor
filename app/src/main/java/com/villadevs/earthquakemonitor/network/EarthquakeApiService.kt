package com.villadevs.earthquakemonitor.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface EarthquakeApiService {
    @GET("all_hour.geojson")
    //suspend fun getPhotos(): List<MarsPhoto>
    suspend fun getLastHourEarthquakes(): String
}

object EarthquakeApi {
    val retrofitService: EarthquakeApiService by lazy {
        retrofit.create(EarthquakeApiService::class.java)
    }
}
