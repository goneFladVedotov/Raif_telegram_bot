package com.raif.onlinecashier

import org.json.JSONException
import java.util.UUID;


fun generateQr(price: Double, marketId: String): QrObject? {
    val response = khttp.post(
        "http://147.78.66.234:8081/payment-api/v1/qrs/dynamic",
        json = mapOf(
            "amount" to price,
            "order" to "cashier.${UUID.randomUUID().toString().replace("-", "")}"
        )
    )
    try {
        val resp = response.jsonObject
        //TODO:
        //  записывать в базу (qrid, qrurl, qrstatus, marketid, price)
        //  создавать поток который следит за оплатой заказа
        return QrObject(
            resp["qrId"].toString(),
            resp["qrUrl"].toString(),
            resp["qrStatus"].toString()
        )
    } catch (e: JSONException) {
        return null
    }
}

fun refund(qrId: String, marketId: String): String {
    //TODO:
    //  check if user own this qr
    //  check if qr has been paid
    //  check
    return "Это плейсхолдер возврата заказа `$qrId`"
}