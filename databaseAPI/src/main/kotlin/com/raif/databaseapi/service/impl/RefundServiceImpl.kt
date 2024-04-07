package com.raif.databaseapi.service.impl

import com.raif.databaseapi.data.RefundRepository
import com.raif.databaseapi.domain.RefundInfo
import com.raif.databaseapi.exception.ResourceNotFoundException
import com.raif.databaseapi.service.RefundService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalStateException

@Service
class RefundServiceImpl(
    private val refundRepository: RefundRepository
): RefundService {
    override fun saveRefundInfo(refundInfo: RefundInfo) {
        if (refundRepository.findByRefundId(refundInfo.refundId) != null) {
            throw IllegalStateException("refund info already exists")
        }
        refundRepository.save(refundInfo)
    }

    @Transactional
    override fun updateRefundStatus(refundId: String, refundStatus: String) {
        val refundInfoToUpdate = refundRepository.findByRefundId(refundId) ?:
        throw ResourceNotFoundException("refund info not found")
        refundInfoToUpdate.status = refundStatus
        refundRepository.saveAndFlush(refundInfoToUpdate)
    }

    override fun getRefundInfo(refundId: String): RefundInfo {
        return refundRepository.findByRefundId(refundId) ?:
        throw ResourceNotFoundException("refund info not found")
    }

    override fun getAll(): List<RefundInfo> {
        return refundRepository.findAll()
    }
}