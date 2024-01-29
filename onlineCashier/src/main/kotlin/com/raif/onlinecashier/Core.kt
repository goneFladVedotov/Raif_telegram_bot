package com.raif.onlinecashier

import org.json.JSONException
import java.util.UUID;


fun generateQr(price: Double, marketId: String): QrObject? {
    println("Send qr post request")
    val response = khttp.post(
        "http://147.78.66.234:8081/payment-api/v1/qrs/dynamic",
        json = mapOf(
            "amount" to price,
            "order" to "cashier.${UUID.randomUUID().toString().replace("-", "")}",
            "qrExpirationDate" to "+1m"
        )
    )
    println("Response received")
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




fun checkPayment(marketId: String, qrId: String, replyTo: Int) {
    val mybot = MyBot()
    while (true) {
        println("Send qr post request")
        val response = khttp.get(
            "http://147.78.66.234:9091/database-api/v1/qrs/$qrId"
        )
        println("Response received")

        try {
            response.jsonObject
        } catch (_: Exception) {
            mybot.sendMessageExecute(marketId, "Ошибка при обновлении статуса qr", replyTo = replyTo)
        }
        val resp = response.jsonObject
        val qrStatus = resp["qrStatus"]
        when (qrStatus) {
            "PAID" -> {
                mybot.sendMessageExecute(marketId, "Qr успешно оплачен", replyTo = replyTo)
            }

            "EXPIRED" -> {
                mybot.sendMessageExecute(marketId, "Qr истек", replyTo = replyTo)
            }

            "NEW", "IN_PROGRESS" -> {
//                mybot.sendMessageExecute(marketId, "Qr еще не оплачен", replyTo = replyTo)
                Thread.sleep(5000)
                continue
            }

            "CANCELED", "INACTIVE" -> {
                mybot.sendMessageExecute(marketId, "Qr отменен", replyTo = replyTo)
            }
        }
        break
    }
}


fun refund(qrId: String, marketId: String): String {
    //TODO:
    //  check if user own this qr
    //  check if qr has been paid
    //  check
    return "Это плейсхолдер возврата заказа `$qrId`"
}