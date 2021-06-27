/*
    API class for Retrofit
 */

package com.sreshtha.jokex.ui.api

import com.sreshtha.jokex.ui.models.JokeData
import retrofit2.Response
import retrofit2.http.GET

interface JokesApi {

    @GET("/random_ten")
    suspend fun getJokes(): Response<List<JokeData>>

}