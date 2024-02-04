package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.PaymentInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.service.PaymentService
import com.raif.databaseapi.web.controller.PaymentController
import com.raif.databaseapi.web.dto.PaymentInfoDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("database-api/v1/payments")
class PaymentControllerImpl(
    private val paymentService: PaymentService,
    private val mapper: Mapper<PaymentInfo, PaymentInfoDto>
): PaymentController {
    @PostMapping
    override fun savePaymentInfo(@RequestBody paymentInfoDto: PaymentInfoDto): ResponseEntity<*> {
        val paymentInfo = mapper.dtoToEntity(paymentInfoDto)
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
    override fun getPaymentInfo(@PathVariable("qrId") qrId: String): ResponseEntity<PaymentInfoDto> {
        val paymentInfoDto = mapper.entityToDto(paymentService.getPaymentInfo(qrId))
        return ResponseEntity.ok(paymentInfoDto)
    }

    @GetMapping
    override fun getAll(): ResponseEntity<List<PaymentInfoDto>> {
        return ResponseEntity.ok(mapper.entityToDto(paymentService.getAll()))
    }

}