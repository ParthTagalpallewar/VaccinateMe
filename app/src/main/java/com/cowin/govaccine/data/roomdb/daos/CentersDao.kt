package com.cowin.govaccine.data.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cowin.govaccine.data.models.roomModels.RoomCenters

@Dao
interface CentersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCenters(centers:List<RoomCenters>)

    @Query("SELECT  * FROM ROOMCENTERS")
    fun getAllCenters():List<RoomCenters>

    @Query("DELETE FROM RoomCenters")
    fun deleteAllCenters()
}