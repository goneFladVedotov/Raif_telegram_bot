package com.raif.databaseapi.mapper

interface Mapper<T, V> {
    fun entityToDto(entity: T): V
    fun entityToDto(entities: List<T>): List<V>
    fun dtoToEntity(dto: V): T
    fun dtoToEntity(dtos: List<V>): List<T>
}