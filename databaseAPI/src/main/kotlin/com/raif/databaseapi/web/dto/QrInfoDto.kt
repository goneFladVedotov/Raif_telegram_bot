package com.raif.databaseapi.web.dto

data class QrInfoDto(
    var qrId: String,
    var qrStatus: String,
    var payload: String,
    var qrUrl: String
)
