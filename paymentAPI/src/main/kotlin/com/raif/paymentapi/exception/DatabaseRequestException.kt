package com.raif.paymentapi.exception

class DatabaseRequestException(
    override val message: String
): Exception(message) {
}