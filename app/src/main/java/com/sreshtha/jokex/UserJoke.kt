package com.sreshtha.jokex

import java.io.Serializable

data class UserJoke(
    val jokeId :String = "",
    val setup :String = "",
    val punchline : String = ""
):Serializable