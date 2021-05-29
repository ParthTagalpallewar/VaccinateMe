package com.cowin.govaccine.data.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cowin.govaccine.data.models.roomModels.RoomSessions

@Dao
interface SessionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSessions(centers:List<RoomSessions>)

    @Query("SELECT  * FROM roomsessions")
    fun getAllSession():List<RoomSessions>

    @Query("SELECT  * FROM roomsessions Where center_id = :centerId")
    fun getSessionByCenterId(centerId:String):List<RoomSessions>

    @Query("DELETE FROM roomsessions")
    fun deleteAllSession()

}