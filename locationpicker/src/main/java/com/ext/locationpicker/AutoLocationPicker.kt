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

    fun init(context: Context) {
        repository = LocationRepository(context.applicationContext)
    }

    fun getCountries(onResult: (List<CountryEntity>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getCountries()
            withContext(Dispatchers.Main) { onResult(result) }
        }
    }

    fun getStates(
        countryName: String,
        countryId: Int,
        onResult: (List<StateEntity>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getStates(countryName, countryId)
            withContext(Dispatchers.Main) { onResult(result) }
        }
    }

    fun getCities(
        countryName: String,
        stateName: String,
        stateId: Int,
        onResult: (List<CityEntity>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getCities(countryName, stateName, stateId)
            withContext(Dispatchers.Main) { onResult(result) }
        }
    }
}

