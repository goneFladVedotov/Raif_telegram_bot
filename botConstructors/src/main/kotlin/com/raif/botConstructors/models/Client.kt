package com.raif.botConstructors.models

data class Client(
    val id: String,
    val type: String,
    val email: String,
    val name: String,
    val extraInfo: Any
)
