package com.raif.paymentapi.domain.model

data class QrInformation(
    var qrId: String,
    var qrStatus: String,
    var payload: String,
    var qrUrl: String
) {
}
