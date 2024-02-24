package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.ReceiptDto
import com.raif.paymentapi.service.DatabaseApiClient
import com.raif.paymentapi.service.ReceiptService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.`in`.fiscal.Receipt
import raiffeisen.sbp.sdk.model.`in`.fiscal.ReceiptClient
import raiffeisen.sbp.sdk.model.`in`.fiscal.ReceiptInfo
import raiffeisen.sbp.sdk.model.`in`.fiscal.ReceiptItem

@Service
class ReceiptServiceImpl(
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String,
    private val databaseApiClient: DatabaseApiClient
) : ReceiptService {
    override fun saveSellReceipt(receiptDto: ReceiptDto): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.saveReceipt(buildReceipt(receiptDto))
    }

    override fun registerSellReceipt(receiptNumber: String): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.registerReceipt(receiptNumber)
    }

    override fun saveRefundReceipt(receiptDto: ReceiptDto): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.saveRefundReceipt(buildReceipt(receiptDto))
    }

    override fun registerRefundReceipt(receiptNumber: String): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.registerRefundReceipt(receiptNumber)
    }

    private fun buildReceipt(receiptDto: ReceiptDto): Receipt {
        val receipt = Receipt()
        receipt.receiptType = receiptDto.receiptType
        val receiptClient = ReceiptClient()
        receiptClient.email = receiptDto.receiptClientDto.email

        val receiptItems: MutableList<ReceiptItem> = mutableListOf()
        for (dto in receiptDto.receiptItemDtos) {
            val item = ReceiptItem()
            item.name = dto.name
            item.price = dto.price
            item.quantity = dto.quantity
            item.amount = dto.amount
            item.vatType = dto.vatType
            receiptItems.add(item)
        }
        receipt.receiptItems = receiptItems.toTypedArray()
        receipt.total = receiptDto.total
        return receipt
    }
}