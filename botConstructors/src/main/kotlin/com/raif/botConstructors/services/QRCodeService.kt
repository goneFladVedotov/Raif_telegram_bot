package com.raif.botConstructors.services

import com.raif.botConstructors.models.QR
interface QRCodeService {
    fun getQR(amount: Double): QR
    fun updateQR(qr: QR): QR
}