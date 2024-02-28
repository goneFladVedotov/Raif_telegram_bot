package com.raif.botConstructors.services

import com.raif.botConstructors.models.Client

interface ClientService {
    fun createClient(client: Client)
    fun getClient(clientId: String): Client

    fun getAll(): List<Client>
}