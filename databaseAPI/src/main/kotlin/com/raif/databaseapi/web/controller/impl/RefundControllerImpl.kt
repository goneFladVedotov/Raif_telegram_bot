package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.RefundInfo
import com.raif.databaseapi.mapper.Mapper
import com.raif.databaseapi.service.RefundService
import com.raif.databaseapi.web.controller.RefundController
import com.raif.databaseapi.web.dto.RefundInfoDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/database-api/v1/refund")
@Tag(name = "Refund Controller")
class RefundControllerImpl(
    private val refundService: RefundService,
    private val mapper: Mapper<RefundInfo, RefundInfoDto>
) : RefundController {
    @Operation(summary = "Сохранение возврата")
    @PostMapping
    override fun saveRefundInfo(@RequestBody refundInfoDto: RefundInfoDto): ResponseEntity<*> {
        val refundInfo = mapper.dtoToEntity(refundInfoDto)
        refundService.saveRefundInfo(refundInfo)
        return ResponseEntity.ok(null)
    }

    @Operation(summary = "Обновление статуса возврата")
    @PutMapping("/{refundId}")
    override fun updateRefundStatus(
        @PathVariable("refundId") refundId: String,
        @RequestBody refundStatus: String
    ): ResponseEntity<*> {
        refundService.updateRefundStatus(refundId, refundStatus)
        return ResponseEntity.ok(null)
    }

    @Operation(summary = "Получение информации о возврате")
    @GetMapping("/{refundId}")
    override fun getRefundInfo(@PathVariable("refundId") refundId: String): ResponseEntity<RefundInfoDto> {
        val refundInfoDto = mapper.entityToDto(refundService.getRefundInfo(refundId))
        return ResponseEntity.ok(refundInfoDto)
    }

    @Operation(summary = "Получение все возвратов")
    @GetMapping
    override fun getAll(): ResponseEntity<List<RefundInfoDto>> {
        return ResponseEntity.ok(mapper.entityToDto(refundService.getAll()))
    }
}