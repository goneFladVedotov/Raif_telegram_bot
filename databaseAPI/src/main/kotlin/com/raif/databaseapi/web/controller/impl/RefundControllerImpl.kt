package com.raif.databaseapi.web.controller.impl

import com.raif.databaseapi.domain.RefundInfo
import com.raif.databaseapi.service.RefundService
import com.raif.databaseapi.web.controller.RefundController
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
@RequestMapping("/database-api/v1/refund")
class RefundControllerImpl(
    private val refundService: RefundService
): RefundController {
    @PostMapping
    override fun saveRefundInfo(@RequestBody refundInfo: RefundInfo): ResponseEntity<*> {
        refundService.saveRefundInfo(refundInfo)
        return ResponseEntity.ok(null)
    }

    @PutMapping("/{refundId}")
    override fun updateRefundStatus(@PathVariable("refundId") refundId: String,
                                    @RequestBody refundStatus: String): ResponseEntity<*> {
        refundService.updateRefundStatus(refundId, refundStatus)
        return ResponseEntity.ok(null)
    }

    @GetMapping("/{refundId}")
    override fun getRefundInfo(@PathVariable("refundId") refundId: String): ResponseEntity<RefundInfo> {
        return ResponseEntity.ok(refundService.getRefundInfo(refundId))
    }

    @GetMapping
    override fun getAll(): ResponseEntity<*> {
        return ResponseEntity.ok(refundService.getAll())
    }
}