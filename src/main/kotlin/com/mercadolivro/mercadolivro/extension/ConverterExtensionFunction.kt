package com.mercadolivro.mercadolivro.extension

import com.mercadolivro.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.mercadolivro.controller.request.PutCostumerRequest
import com.mercadolivro.mercadolivro.enum.BookStatus
import com.mercadolivro.mercadolivro.enum.CostumerStatus
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CostumerModel

fun PostCostumerRequest.toCostumerModel(): CostumerModel{
    return CostumerModel(name = this.name, email = this.email, status = CostumerStatus.ATIVO)
}

fun String.primeiraLetra(): Char{
    return this.get(0)
}

fun PutCostumerRequest.toCostumerModel(previousValue: CostumerModel): CostumerModel{
    return CostumerModel(previousValue.id, name, email, previousValue.status)
}

fun PostBookRequest.toBookModel(costumer: CostumerModel): BookModel{
    return BookModel(
        name = this.name,
        price = this.price,
        status = BookStatus.ATIVO,
        costumerId = costumer
    )
}
fun PutBookRequest.toBookModel(previousValue: BookModel): BookModel{
    return BookModel(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        price = this.price ?: previousValue.price,
        status = previousValue.status,
        costumerId = previousValue.costumerId
    )
}
