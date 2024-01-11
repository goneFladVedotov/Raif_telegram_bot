package com.raif.databaseapi.service

import com.raif.databaseapi.domain.RefundInfo

interface RefundService {
    fun saveRefundInfo(refundInfo: RefundInfo)
    fun updateRefundStatus(refundId: String, refundStatus: String)
    fun getRefundInfo(refundId: String): RefundInfo
}