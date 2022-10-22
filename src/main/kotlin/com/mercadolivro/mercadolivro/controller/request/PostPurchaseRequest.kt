package com.mercadolivro.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class PostPurchaseRequest(

    @field:NotNull
    @field:Positive
    @JsonAlias("costumer_id")
    val costumerId: Int,

    @field:NotNull
    @JsonAlias("book_id")
    val bookId: Set<Int>
)