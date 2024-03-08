package com.raif.onlinecashier.models.repositories

import com.raif.onlinecashier.models.QrObject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QrObjectRepository : JpaRepository<QrObject, String> {
}