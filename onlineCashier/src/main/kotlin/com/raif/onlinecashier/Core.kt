package com.raif.onlinecashier

fun generateQr(price: Float, marketId: String, ): QrObject {
    //TODO:
    //  записывать в базу (qrid, qrurl, qrstatus, marketid, price)
    //  создавать поток который следит за оплатой заказа
    return QrObject(
        "Это плейсхолдер сгенерированного QR",
        "https://test.ecom.raiffeisen.ru/api/sbp/v1/qr/ADE7A03509AA403790136E0ADF41C1C9/image",
        "NEW"
    )
}

fun refund (qrId: String, marketId: String, ) : String {
    //TODO:
    //  check if user own this qr
    //  check if qr has been paid
    //  check
    return "Это плейсхолдер возврата "
}