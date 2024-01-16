package com.raif.databaseapi.mapper

interface Mapper<T, V> {
    fun entityToDto(entity: T): V
    fun dtoToEntity(dto: V): T
}