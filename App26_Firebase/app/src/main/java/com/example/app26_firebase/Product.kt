package com.example.app26_firebase

data class Product(
    var pId: Int,
    var pName: String,
    var pQuantity: Int
) {
    constructor() : this(0, "noinfo", 0)
}