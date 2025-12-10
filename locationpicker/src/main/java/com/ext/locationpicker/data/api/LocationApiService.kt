package com.ext.locationpicker.data.api

import com.ext.locationpicker.data.model.City
import com.ext.locationpicker.data.model.Country
import com.ext.locationpicker.data.model.State
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApiService {

    @GET("countries")
    suspend fun getCountries(): List<Country>

    @GET("states")
    suspend fun getStates(
        @Query("country_id") countryId: Int
    ): List<State>

    @GET("cities")
    suspend fun getCities(
        @Query("state_id") stateId: Int
    ): List<City>
}
