package com.cowin.govaccine.data.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cowin.govaccine.data.models.SettingsModel
import com.cowin.govaccine.data.models.roomModels.RoomCenters
import com.cowin.govaccine.data.models.roomModels.RoomSessions
import com.cowin.govaccine.data.roomdb.daos.CentersDao
import com.cowin.govaccine.data.roomdb.daos.SessionsDao
import com.cowin.govaccine.data.roomdb.daos.UserDao


@Database(
    entities = [SettingsModel::class,RoomCenters::class,RoomSessions::class],
    version = 1
)
abstract class  AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getCentersDao(): CentersDao
    abstract fun getSessionDao(): SessionsDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build()
    }
}