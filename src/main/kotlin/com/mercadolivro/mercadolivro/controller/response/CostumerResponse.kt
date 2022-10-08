package com.mercadolivro.mercadolivro.controller.response

import com.mercadolivro.mercadolivro.enum.CostumerStatus

data class CostumerResponse (
    val id: Int?,
    val name: String,
    val email: String,
    val status: CostumerStatus
        ){
}