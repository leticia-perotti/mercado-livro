package com.mercadolivro.mercadolivro.enum

enum class Errors(val code: String, val message: String) {
    ML001("ML-001", "Invalid request"),

    ML101("ML-101", "Book [%s] not exist"),
    ML102("ML-102", "Can not update book with status [%s]"),

    ML201("ML-201", "Costumer [%s] not exist")
}