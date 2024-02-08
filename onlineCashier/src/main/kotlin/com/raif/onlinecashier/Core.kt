package com.raif.onlinecashier

import org.json.JSONException
import org.json.JSONObject
import java.util.UUID


fun qrFromJson(json: JSONObject): QrObject {
    return QrObject(
        json["qrId"].toString(),
        json["qrUrl"].toString(),
        json["payload"].toString(),
        json["qrStatus"].toString(),
        "",
    )
}


fun generateQr(price: Double, marketId: String): QrObject? {
    println("Send qr post request")
    val uuid = UUID.randomUUID().toString().replace("-", "")
    val orderId = "cashier.$uuid"
    val response = khttp.post(
        "http://147.78.66.234:8081/payment-api/v1/qrs/dynamic",
        json = mapOf(
            "amount" to price,
            "order" to orderId,
            "qrExpirationDate" to "+1M"
        )
    )
    println("Response received")
    try {
        //TODO:
        //  записывать в базу (qrid, qrurl, qrstatus, marketid, price)
        //  создавать поток который следит за оплатой заказа
        val resp = response.jsonObject
        val qr = qrFromJson(resp)
        qr.orderId = orderId

        return qr
    } catch (e: JSONException) {
        return null
    }
}

fun getQrById(qrId: String): QrObject? {
    println("Send get qr=$qrId request")
    val response = khttp.get(
        "http://147.78.66.234:9091/database-api/v1/qrs/$qrId"
    )
    println("Response received")
    val qr = try {
        qrFromJson(response.jsonObject)
    } catch (_: JSONException) {
        return null
    }
    val qrn = loadQrByQrId(qr.qrId)
    if (qrn == null) {
        qr.orderId = "CANT FIND"
    } else {
        qr.orderId = qrn.orderId
    }
    return qr

}


class CheckPayment(private var marketId: String, private var qrId: String, private var replyTo: Int) : Runnable {
    override fun run() {
        val mybot = MyBot()
        while (true) {
            val qr = getQrById(qrId)
            if (qr == null) {
                mybot.sendMessageExecute(marketId, "Ошибка при обновлении статуса qr", replyTo = replyTo)
                break
            }
            println(qr.qrStatus)

            val qrStatus = qr.qrStatus
            when (qrStatus) {
                "PAID" -> {
                    mybot.sendMessageExecute(marketId, "Qr успешно оплачен", replyTo = replyTo)
                }

                "EXPIRED" -> {
                    mybot.sendMessageExecute(marketId, "Qr истек", replyTo = replyTo)
                }

                "NEW", "IN_PROGRESS" -> {
                    //mybot.sendMessageExecute(marketId, "Qr еще не оплачен", replyTo = replyTo)
                    Thread.sleep(1000)
                    continue
                }

                "CANCELED", "INACTIVE" -> {
                    mybot.sendMessageExecute(marketId, "Qr отменен", replyTo = replyTo)
                }
            }
            break
        }
    }
}


fun refund(qrId: String, marketId: String): String {
    //TODO:
    //  check if user own this qr
    //  check if qr has been paid
    //  check
    return "Это плейсхолдер возврата заказа `$qrId`"
}