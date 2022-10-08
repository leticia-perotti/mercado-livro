package com.mercadolivro.mercadolivro.controller.response

import com.mercadolivro.mercadolivro.enum.BookStatus
import com.mercadolivro.mercadolivro.model.CostumerModel
import java.math.BigDecimal

data class BookResponse(
    val id: Int? = null,
    val name: String,
    val price: BigDecimal,
    val costumer: CostumerModel? = null,
    val status: BookStatus? =null
) {
}