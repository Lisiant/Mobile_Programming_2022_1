package com.example.clone10

import java.io.Serializable

data class MyData(
    val word:String,
    val meaning: String,
    var isOpen: Boolean = false
):Serializable
