package com.raif.databaseapi.domain

import jakarta.persistence.*

@Entity
@Table(name = "subscription_info")
class SubscriptionInfo(
    @Column(name = "subscription_id")
    var subscriptionId: String,
    @Column(name = "create_date")
    var createDate: String,
    var status: String,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qr_id", referencedColumnName = "id")
    var qrInfo: QrInfo
) : BaseEntity() {
}