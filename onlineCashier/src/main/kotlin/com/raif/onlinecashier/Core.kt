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


fun generateUuid(): String {
    return UUID.randomUUID().toString().replace("-", "")
}

fun generateQr(price: Double, marketId: String): QrObject? {
    println("Send qr post request")
    val uuid = generateUuid()
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
    val resp = try {
        response.jsonObject

    } catch (e: JSONException) {
        return null
    }
    val qr = qrFromJson(resp)
    qr.orderId = orderId
    storeQr(qr)
    return qr

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


fun refund(qrId: String, marketId: String, price: Double): String {
    val qr = loadQrByQrId(qrId) ?: return "Не смог найти qr по id `$qrId`"
    val response = refundRequest(
        mapOf(
            "orderId" to qr.orderId,
            "refundId" to generateUuid(),
            "amount" to price
        )
    ) ?: return "Ошибка при выполнении возврата"
    return "Возврат: ${response["amount"]}\nСтатус: ${response["refundStatus"]}"
}

fun refundRequest(json: Map<String, Any>): JSONObject? {
    return JSONObject(mutableMapOf(
        "amount" to json.getOrDefault("amount", 0),
        "refundStatus" to "PLACEHOLDER OK"
    ))

    val response = khttp.post(
        "http://147.78.66.234:8081/payment-api/v1/qrs/refund",
        json = json
    )
    return try {
        response.jsonObject
    } catch (_: JSONException) {
        null
    }
}