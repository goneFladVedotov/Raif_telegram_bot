package com.raif.paymentapi.service.impl

import com.raif.paymentapi.domain.dto.ReceiptDto
import com.raif.paymentapi.service.ReceiptService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import raiffeisen.sbp.sdk.client.SbpClient
import raiffeisen.sbp.sdk.model.fiscal.Receipt
import raiffeisen.sbp.sdk.model.fiscal.ReceiptClient
import raiffeisen.sbp.sdk.model.fiscal.ReceiptInfo
import raiffeisen.sbp.sdk.model.fiscal.ReceiptItem

@Service
class ReceiptServiceImpl(
    @Value("\${raif.sbpMerchantId}")
    private val sbpMerchantId: String,
    @Value("\${raif.secretKey}")
    private val secretKey: String
) : ReceiptService {
    override fun saveSellReceipt(receiptDto: ReceiptDto): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.saveSellReceipt(buildReceipt(receiptDto))
    }

    override fun registerSellReceipt(receiptNumber: String): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.registerSellReceipt(receiptNumber)
    }

    override fun getSellReceipt(receiptNumber: String): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.getSellReceiptInfo(receiptNumber)
    }

    override fun saveRefundReceipt(receiptDto: ReceiptDto): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.saveRefundReceipt(buildReceipt(receiptDto))
    }

    override fun registerRefundReceipt(receiptNumber: String): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.registerRefundReceipt(receiptNumber)
    }

    override fun getRefundReceipt(receiptNumber: String): ReceiptInfo {
        val sbpClient = SbpClient(SbpClient.FISCAL_TEST_URL, sbpMerchantId, secretKey)
        return sbpClient.getRefundReceiptInfo(receiptNumber)
    }

    private fun buildReceipt(receiptDto: ReceiptDto): Receipt {
        val receipt = Receipt()
        receipt.receiptNumber = receiptDto.receiptNumber
        val receiptClient = ReceiptClient()
        receiptClient.email = receiptDto.email

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
        receipt.client = receiptClient
        receipt.items = receiptItems.toTypedArray()
        receipt.total = receiptDto.total
        return receipt
    }
}