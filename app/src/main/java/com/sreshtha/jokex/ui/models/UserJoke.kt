/*
  Class to represent user created jokes which will be later stored on firebase
 */

package com.sreshtha.jokex.ui.models

import java.io.Serializable

data class UserJoke(
    val jokeId: String = "",
    val setup: String = "",
    val punchline: String = ""
) : Serializable