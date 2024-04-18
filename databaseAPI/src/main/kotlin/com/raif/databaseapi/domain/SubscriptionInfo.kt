package com.raif.databaseapi.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "subscription_info")
class SubscriptionInfo(
    @Column(name = "subscription_id")
    var subscriptionId: String,
    @Column(name = "create_date")
    var createDate: String,
    var status: String,
    @OneToOne
    @JoinColumn(name = "qr_id", referencedColumnName = "id")
    var qrInfo: QrInfo
) : BaseEntity() {
}