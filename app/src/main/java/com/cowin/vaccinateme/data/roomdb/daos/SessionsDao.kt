package com.cowin.vaccinateme.data.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cowin.vaccinateme.data.models.roomModels.RoomSessions

@Dao
interface SessionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCenters(centers:List<RoomSessions>)

    @Query("SELECT  * FROM ROOMCENTERS")
    fun getAllCenters():List<RoomSessions>

}