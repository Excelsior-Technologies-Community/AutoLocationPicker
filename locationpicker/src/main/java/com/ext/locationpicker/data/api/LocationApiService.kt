package com.ext.locationpicker.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// ✅ RESPONSE MODELS

data class CountryListResponse(
    val data: List<CountryItem>
)

data class CountryItem(
    val country: String
)

data class StateResponse(
    val data: StateData
)

data class StateData(
    val states: List<StateItem>
)

data class StateItem(
    val name: String
)

data class CityResponse(
    val data: List<String>
)

// ✅ REQUEST MODELS
data class CountryRequest(val country: String)
data class StateRequest(val country: String, val state: String)

// ✅ API INTERFACE
interface LocationApiService {

    // ✅ GET ALL COUNTRIES (CORRECT ENDPOINT)
    @GET("countries")
    suspend fun getCountries(): CountryListResponse

    // ✅ GET STATES BY COUNTRY
    @POST("countries/states")
    suspend fun getStates(@Body body: CountryRequest): StateResponse

    // ✅ GET CITIES BY STATE
    @POST("countries/state/cities")
    suspend fun getCities(@Body body: StateRequest): CityResponse
}
