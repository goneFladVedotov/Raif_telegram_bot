package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.PaymentInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.service.PaymentService
import com.raif.databaseapi.web.controller.PaymentController
import com.raif.databaseapi.web.dto.PaymentInfoDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("database-api/v1/payments")
@Tag(name = "Payment Controller")
class PaymentControllerImpl(
    private val paymentService: PaymentService,
    private val mapper: Mapper<PaymentInfo, PaymentInfoDto>
): PaymentController {
    @Operation(summary = "Сохранение платежа")
    @PostMapping
    override fun savePaymentInfo(@RequestBody paymentInfoDto: PaymentInfoDto): ResponseEntity<*> {
        val paymentInfo = mapper.dtoToEntity(paymentInfoDto)
        paymentService.savePaymentInfo(paymentInfo)
        return ResponseEntity.ok(null)
    }

    @Operation(summary = "Обновление статуса платежа")
    @PatchMapping("/{qrId}")
    override fun updatePaymentInfo(@PathVariable("qrId") qrId: String,
                                   @RequestBody paymentStatus: String): ResponseEntity<*> {
        paymentService.updatePaymentInfo(qrId, paymentStatus)
        return ResponseEntity.ok(null)
    }

    @Operation(summary = "Получение информации о платеже")
    @GetMapping("/{qrId}")
    override fun getPaymentInfo(@PathVariable("qrId") qrId: String): ResponseEntity<PaymentInfoDto> {
        val paymentInfoDto = mapper.entityToDto(paymentService.getPaymentInfo(qrId))
        return ResponseEntity.ok(paymentInfoDto)
    }

    @Operation(summary = "Получение всех платежей")
    @GetMapping
    override fun getAll(): ResponseEntity<List<PaymentInfoDto>> {
        return ResponseEntity.ok(mapper.entityToDto(paymentService.getAll()))
    }

}