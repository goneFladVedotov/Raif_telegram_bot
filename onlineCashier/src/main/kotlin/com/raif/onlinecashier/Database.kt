package com.raif.onlinecashier

var tmp_db: MutableMap<String, QrObject> = mutableMapOf()
fun storeQr(qr: QrObject) {
    //TODO:
    //  записывать в базу (qrid, qrurl, qrstatus, marketid, price)
    tmp_db[qr.qrId] = qr
    return
}
fun loadQrByOrderId(orderId: String)  : QrObject? {
    return null
}
fun loadQrByQrId(qrId: String) : QrObject? {
    if (tmp_db.contains(qrId)) {
        return tmp_db[qrId]
    } else {
        return null
    }
}