package com.raif.botConstructors.services.impl

import com.raif.botConstructors.models.Client
import com.raif.botConstructors.models.Order
import com.raif.botConstructors.services.ClientService
import org.springframework.stereotype.Service

@Service
class ClientServiceImpl() : ClientService {
    val clientMap: MutableMap<String, Client> = mutableMapOf()
    override fun createClient(client: Client) {
        clientMap[client.id] = client
    }
    override fun getClient(clientId: String): Client {
        val client: Client = clientMap.getOrElse(clientId) {
            throw Exception("not valid clientId")
        }
        return client
    }

    override fun getAll(): List<Client> {
        return clientMap.values.toList()
    }
}