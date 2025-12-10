package com.ext.locationpicker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.ext.locationpicker.data.db.dao.LocationDao
import com.ext.locationpicker.data.db.entity.CityEntity
import com.ext.locationpicker.data.db.entity.CountryEntity
import com.ext.locationpicker.data.db.entity.StateEntity

@Database(
    entities = [CountryEntity::class, StateEntity::class, CityEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocationDatabase : RoomDatabase() {

    abstract fun locationDao(): LocationDao

    companion object {

        @Volatile
        private var INSTANCE: LocationDatabase? = null

        fun getInstance(context: Context): LocationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
                    "location_picker_db_v2" // ✅ changed name to force rebuild
                ).addCallback(object : RoomDatabase.Callback() {

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = getInstance(context).locationDao()

                            // ✅ DUMMY COUNTRIES
                            val countries = listOf(
                                CountryEntity(1, "India", "IN"),
                                CountryEntity(2, "USA", "US")
                            )

                            // ✅ DUMMY STATES
                            val states = listOf(
                                StateEntity(1, "Gujarat", 1),
                                StateEntity(2, "Maharashtra", 1),
                                StateEntity(3, "California", 2)
                            )

                            // ✅ DUMMY CITIES
                            val cities = listOf(
                                CityEntity(1, "Surat", 1),
                                CityEntity(2, "Ahmedabad", 1),
                                CityEntity(3, "Mumbai", 2),
                                CityEntity(4, "Los Angeles", 3)
                            )

                            dao.insertCountries(countries)
                            dao.insertStates(states)
                            dao.insertCities(cities)
                        }
                    }

                }).build()

                INSTANCE = instance
                instance
            }
        }

    }
}
