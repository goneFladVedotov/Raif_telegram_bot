package com.raif.paymentapi.domain.model

data class SubscriptionInformation(
    var subscriptionId: String,
    var createDate: String,
    var status: String,
    var qrId: String,
    var payload: String,
    var url: String
)
