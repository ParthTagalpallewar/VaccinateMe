package com.cowin.vaccinateme.data.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cowin.vaccinateme.data.models.roomModels.RoomCenters
import retrofit2.http.GET

@Dao
interface CentersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCenters(centers:List<RoomCenters>)

    @Query("SELECT  * FROM ROOMCENTERS")
    fun getAllCenters():List<RoomCenters>
}