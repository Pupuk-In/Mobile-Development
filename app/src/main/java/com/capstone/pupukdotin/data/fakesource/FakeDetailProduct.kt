package com.capstone.pupukdotin.data.fakesource

data class FakeDetailProduct(
    val name: String,
    val image: String,
    val price: Int,
    val rating: String,
    val sold: Int,
    val description: String,
    val plant: List<String>,
    val plantFor: List<String>,
    val store: FakeStore
)
