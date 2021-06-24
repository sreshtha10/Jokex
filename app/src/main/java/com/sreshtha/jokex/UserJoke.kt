package com.sreshtha.jokex

import java.io.Serializable

data class UserJoke(
    val setup :String = "",
    val punchline : String = ""
):Serializable