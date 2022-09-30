package com.mercadolivro.mercadolivro.extension

import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.controller.request.PutCostumerRequest
import com.mercadolivro.mercadolivro.model.CostumerModel

fun PostCostumerRequest.toCostumerModel(): CostumerModel{
    return CostumerModel(name = this.name, email = this.email)
}

fun String.primeiraLetra(): Char{
    return this.get(0)
}

fun PutCostumerRequest.toCostumerModel(id: Int): CostumerModel{
    return CostumerModel(id, name, email)
}