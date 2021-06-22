/*
    API class for Retrofit
 */

package com.sreshtha.jokex

import retrofit2.Response
import retrofit2.http.GET

interface JokesApi {

    @GET("/random_ten")
    suspend  fun getJokes() : Response<List<JokeData>>

}