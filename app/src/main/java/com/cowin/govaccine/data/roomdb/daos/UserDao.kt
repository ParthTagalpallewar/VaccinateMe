package com.cowin.govaccine.data.roomdb.daos

import androidx.room.*
import com.cowin.govaccine.data.models.SettingsModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createSettings(user: SettingsModel): Long

    @Query("SELECT * FROM SettingsModel")
    suspend fun getSettings(): SettingsModel?

    @Update()
    suspend fun updateSettings(user: SettingsModel)


}