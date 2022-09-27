package com.mercadolivro.mercadolivro.controller.request

import com.mercadolivro.mercadolivro.model.CostumerModel

data class PostCostumerRequest(
    val name: String,
    val email: String
) {
}