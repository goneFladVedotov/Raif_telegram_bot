package com.raif.databaseapi.web.dto

data class SubscriptionDto(
    var subscriptionId: String,
    var createDate: String,
    var status: String,
    var qrId: String,
    var payload: String,
    var url: String
)
