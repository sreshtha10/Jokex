/*
    Data Class to represent JSON data received from the API
 */

package com.sreshtha.jokex


data class JokeData(
    val id: Int,
    val punchline: String,
    val setup: String,
    val type: String
)