package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.PaymentInfo
import com.raif.databaseapi.service.PaymentService
import com.raif.databaseapi.web.controller.PaymentController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @PutMapping("/{qrId}")
    override fun updatePaymentInfo(@PathVariable("qrId") qrId: String,
                                   @RequestBody paymentStatus: String): ResponseEntity<*> {
        paymentService.updatePaymentInfo(qrId, paymentStatus)
        return ResponseEntity.ok(null)
    }

    @GetMapping("/{qrId}")
    override fun getPaymentInfo(@PathVariable("qrId") qrId: String): ResponseEntity<*> {
        return ResponseEntity.ok(paymentService.getPaymentInfo(qrId))
    }

    @GetMapping
    override fun getAll(): ResponseEntity<*> {
        return ResponseEntity.ok(paymentService.getAll())
    }

}