package com.mercadolivro.mercadolivro.controller.request

import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCostumerRequest(

    @field:NotEmpty(message = "O nome não deve ser vazio")
    val name: String,

    @field:Email(message = "Email deve válido")
    @EmailAvailable
    val email: String,

    @field:NotEmpty(message = "A senha deve ser informada")
    val password: String
) {
}