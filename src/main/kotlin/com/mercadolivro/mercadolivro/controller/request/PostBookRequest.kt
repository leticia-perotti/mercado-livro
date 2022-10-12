package com.mercadolivro.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PostBookRequest(

    @field:NotEmpty(message = "O nome deve ser informado")
    val name: String,

    @field:NotNull(message = "Price deve ser informado")
    val price : BigDecimal,

    @JsonAlias("costumer_id")
    val costumerId: Int
) {
}