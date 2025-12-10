package com.ext.locationpicker.domain.repository

import android.content.Context
import com.ext.locationpicker.data.api.RetrofitClient
import com.ext.locationpicker.data.db.LocationDatabase
import com.ext.locationpicker.data.db.entity.CityEntity
import com.ext.locationpicker.data.db.entity.CountryEntity
import com.ext.locationpicker.data.db.entity.StateEntity
import com.ext.locationpicker.data.model.City
import com.ext.locationpicker.data.model.Country
import com.ext.locationpicker.data.model.State

class LocationRepository(context: Context) {

    private val api = RetrofitClient.api
    private val dao = LocationDatabase.getInstance(context).locationDao()

    // ✅ COUNTRIES
//    suspend fun getCountries(forceRefresh: Boolean = false): List<CountryEntity> {
//        return try {
//            if (forceRefresh) {
//                syncCountries()
//            }
//
//            val local = dao.getCountries()
//            if (local.isNotEmpty()) {
//                local
//            } else {
//                syncCountries()
//                dao.getCountries()
//            }
//
//        } catch (e: Exception) {
//            dao.getCountries() // fallback to cache
//        }
//    }

    suspend fun getCountries(): List<CountryEntity> {
        return dao.getCountries()
    }


    // ✅ STATES
//    suspend fun getStates(countryId: Int, forceRefresh: Boolean = false): List<StateEntity> {
//        return try {
//            if (forceRefresh) {
//                syncStates(countryId)
//            }
//
//            val local = dao.getStates(countryId)
//            if (local.isNotEmpty()) {
//                local
//            } else {
//                syncStates(countryId)
//                dao.getStates(countryId)
//            }
//
//        } catch (e: Exception) {
//            dao.getStates(countryId)
//        }
//    }

    suspend fun getStates(countryId: Int): List<StateEntity> {
        return dao.getStates(countryId)
    }


    // ✅ CITIES
//    suspend fun getCities(stateId: Int, forceRefresh: Boolean = false): List<CityEntity> {
//        return try {
//            if (forceRefresh) {
//                syncCities(stateId)
//            }
//
//            val local = dao.getCities(stateId)
//            if (local.isNotEmpty()) {
//                local
//            } else {
//                syncCities(stateId)
//                dao.getCities(stateId)
//            }
//
//        } catch (e: Exception) {
//            dao.getCities(stateId)
//        }
//    }

    suspend fun getCities(stateId: Int): List<CityEntity> {
        return dao.getCities(stateId)
    }


    // ✅ SYNC FROM API → ROOM

    private suspend fun syncCountries() {
        val response: List<Country> = api.getCountries()
        val entities = response.map {
            CountryEntity(
                id = it.id,
                name = it.name,
                iso2 = it.iso2
            )
        }
        dao.clearCountries()
        dao.insertCountries(entities)
    }

    private suspend fun syncStates(countryId: Int) {
        val response: List<State> = api.getStates(countryId)
        val entities = response.map {
            StateEntity(
                id = it.id,
                name = it.name,
                countryId = it.countryId
            )
        }
        dao.insertStates(entities)
    }

    private suspend fun syncCities(stateId: Int) {
        val response: List<City> = api.getCities(stateId)
        val entities = response.map {
            CityEntity(
                id = it.id,
                name = it.name,
                stateId = it.stateId
            )
        }
        dao.insertCities(entities)
    }
}
