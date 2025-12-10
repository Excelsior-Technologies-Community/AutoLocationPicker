package com.ext.locationpicker

import android.content.Context
import com.ext.locationpicker.data.db.entity.CityEntity
import com.ext.locationpicker.data.db.entity.CountryEntity
import com.ext.locationpicker.data.db.entity.StateEntity
import com.ext.locationpicker.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object AutoLocationPicker {

    private lateinit var repository: LocationRepository

    // ✅ Must be called once from App
    fun init(context: Context) {
        repository = LocationRepository(context.applicationContext)
    }

    // ✅ Get Countries
    fun getCountries(
        forceRefresh: Boolean = false,
        onResult: (List<CountryEntity>) -> Unit,
        onError: (Throwable) -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val countries = repository.getCountries(forceRefresh)
                val countries = repository.getCountries()
                withContext(Dispatchers.Main) {
                    onResult(countries)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }

    // ✅ Get States
    fun getStates(
        countryId: Int,
        forceRefresh: Boolean = false,
        onResult: (List<StateEntity>) -> Unit,
        onError: (Throwable) -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val states = repository.getStates(countryId, forceRefresh)
                val states = repository.getStates(countryId)
                withContext(Dispatchers.Main) {
                    onResult(states)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }

    // ✅ Get Cities
    fun getCities(
        stateId: Int,
        forceRefresh: Boolean = false,
        onResult: (List<CityEntity>) -> Unit,
        onError: (Throwable) -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val cities = repository.getCities(stateId, forceRefresh)
                val cities = repository.getCities(stateId)
                withContext(Dispatchers.Main) {
                    onResult(cities)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError(e)
                }
            }
        }
    }
}
