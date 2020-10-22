package de.markus.learning.domain.util

interface IMapper<T, U> {
    fun convertModelToDTO(model: T): U
    fun convertDTOToModel(dto: U): T
}

fun <T, U> IMapper<T, U>.convertDTOsToModels(dtos: List<U>): List<T> {
    val result = mutableListOf<T>()
    for (dto in dtos) {
        result.add(convertDTOToModel(dto))
    }
    return result
}

fun <T, U> IMapper<T, U>.convertModelsToDTOs(models: List<T>): List<U> {
    val result = mutableListOf<U>()
    for (model in models) {
        result.add(convertModelToDTO(model))
    }
    return result
}