package com.raif.botConstructors.services
import com.raif.botConstructors.models.Order
import com.raif.botConstructors.models.dto.RefundDto

interface ReceiptService {
    fun createSellReceipt(order: Order)

    fun createRefundReceipt(order: Order, refundDto: RefundDto)
}