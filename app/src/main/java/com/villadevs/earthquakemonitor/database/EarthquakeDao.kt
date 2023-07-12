package com.villadevs.earthquakemonitor.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.villadevs.earthquakemonitor.model.Earthquake
import kotlinx.coroutines.flow.Flow

@Dao
interface EarthquakeDao {

    //Insertar en la base de datos la lista de terremotos descargados de Internet
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllEarthquakes(earthqueakeList: MutableList<Earthquake>)


    //Extraer de la base de datos la lista de terremotos
    @Query("SELECT * FROM earthquakes")
    fun getEarthquakes(): MutableList<Earthquake>
    //fun getEarthquakes(): Flow<List<Earthquake>>

    //Ordenar los terremotos por la magnitud
    @Query("SELECT * FROM earthquakes ORDER BY magnitude ASC")
    fun getEarthquakeByMagnitude(): MutableList<Earthquake>





    //Extraer sólo los terremotos que sean mayor de una magnitud que defininamos
    @Query("SELECT * FROM earthquakes WHERE magnitude = :magnitudeChoose ORDER BY magnitude ASC")
    fun getEartquakeMagnitude(magnitudeChoose: Double): MutableList<Earthquake>


    /*@Query("SELECT * FROM earthquakes ORDER BY arrival_time ASC")
    fun getAll(): Flow<List<Earthquake>>


    @Query("SELECT * FROM earthquakes WHERE stop_name = :stopName ORDER BY arrival_time ASC")
    fun getByStopName(stopName: String): Flow<List<Earthquake>>*/

}