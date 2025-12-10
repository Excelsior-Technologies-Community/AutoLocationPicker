package com.ext.locationpicker.data.db.dao

import androidx.room.*
import com.ext.locationpicker.data.db.entity.CityEntity
import com.ext.locationpicker.data.db.entity.CountryEntity
import com.ext.locationpicker.data.db.entity.StateEntity

@Dao
interface LocationDao {

    // ✅ Insert (Auto-replace on update)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(list: List<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStates(list: List<StateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(list: List<CityEntity>)

    // ✅ Fetch
    @Query("SELECT * FROM countries ORDER BY name ASC")
    suspend fun getCountries(): List<CountryEntity>

    @Query("SELECT * FROM states WHERE countryId = :countryId ORDER BY name ASC")
    suspend fun getStates(countryId: Int): List<StateEntity>

    @Query("SELECT * FROM cities WHERE stateId = :stateId ORDER BY name ASC")
    suspend fun getCities(stateId: Int): List<CityEntity>

    // ✅ Optional: Clear old data
    @Query("DELETE FROM countries")
    suspend fun clearCountries()

    @Query("DELETE FROM states")
    suspend fun clearStates()

    @Query("DELETE FROM cities")
    suspend fun clearCities()
}
