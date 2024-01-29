package com.raif.onlinecashier


data class QrObject(
    val qrId: String,
    val qrUrl: String,
    val payload: String,
    val qrStatus: String,
)

