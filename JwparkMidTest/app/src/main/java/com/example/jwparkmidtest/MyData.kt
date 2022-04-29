package com.example.jwparkmidtest

import java.io.Serializable

data class MyData(
    val name: String,
    val companyName: String,
    val phoneNumber: String,
    var count: Int = 0
) : Serializable
