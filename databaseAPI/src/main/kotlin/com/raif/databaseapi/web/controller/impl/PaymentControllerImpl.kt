package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.PaymentInfo
import com.raif.databaseapi.service.PaymentService
import com.raif.databaseapi.web.controller.PaymentController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("database-api/v1/payments")
class PaymentControllerImpl(
    private val paymentService: PaymentService
): PaymentController {
    @PostMapping
    override fun savePaymentInfo(@RequestBody paymentInfo: PaymentInfo): ResponseEntity<*> {
        paymentService.savePaymentInfo(paymentInfo)
        return ResponseEntity.ok(null)
    }

    @PutMapping
    override fun updatePaymentInfo(@RequestParam("qr_id") qrId: String,
                                   @RequestParam("payment_status") paymentStatus: String): ResponseEntity<*> {
        paymentService.updatePaymentInfo(qrId, paymentStatus)
        return ResponseEntity.ok(null)
    }

    @GetMapping("/{qrId}")
    override fun getPaymentInfo(@PathVariable("qrId") qrId: String): ResponseEntity<*> {
        return ResponseEntity.ok(paymentService.getPaymentInfo(qrId))
    }

}