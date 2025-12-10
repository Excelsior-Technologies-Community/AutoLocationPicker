package com.ext.locationpicker.domain.repository

import android.content.Context
import com.ext.locationpicker.data.api.CountryRequest
import com.ext.locationpicker.data.api.RetrofitClient
import com.ext.locationpicker.data.api.StateRequest
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

    // ✅ COUNTRIES (API → ROOM → UI)
    suspend fun getCountries(): List<CountryEntity> {
        return try {
            val response = api.getCountries()

            val countries = response.data.mapIndexed { index, item ->
                CountryEntity(
                    id = index + 1,
                    name = item.country, // ✅ FIX IS HERE
                    iso2 = item.country.take(2).uppercase()
                )
            }

            dao.clearCountries()
            dao.insertCountries(countries)
            dao.getCountries()

        } catch (e: Exception) {
            // ✅ Offline fallback
            dao.getCountries()
        }
    }


    // ✅ STATES
    suspend fun getStates(countryName: String, countryId: Int): List<StateEntity> {
        return try {
            val response = api.getStates(CountryRequest(countryName))

            val states = response.data.states.mapIndexed { index, item ->
                StateEntity(index + 1, item.name, countryId)
            }
            dao.clearStates()
            dao.insertStates(states)
            dao.getStates(countryId)
        } catch (e: Exception) {
            dao.getStates(countryId)
        }
    }

    // ✅ CITIES
    suspend fun getCities(countryName: String, stateName: String, stateId: Int): List<CityEntity> {
        return try {
            val response = api.getCities(StateRequest(countryName, stateName))

            val cities = response.data.mapIndexed { index, name ->
                CityEntity(index + 1, name, stateId)
            }
            dao.clearCities()
            dao.insertCities(cities)
            dao.getCities(stateId)
        } catch (e: Exception) {
            dao.getCities(stateId)
        }
    }
}

