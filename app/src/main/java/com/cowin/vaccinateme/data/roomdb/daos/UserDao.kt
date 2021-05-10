package com.cowin.vaccinateme.data.roomdb.daos

import androidx.room.*
import com.cowin.vaccinateme.data.models.SettingsModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createSettings(user: SettingsModel): Long

    @Query("SELECT * FROM SettingsModel")
    suspend fun getSettings(): SettingsModel?

    @Update()
    suspend fun updateSettings(user: SettingsModel)


}