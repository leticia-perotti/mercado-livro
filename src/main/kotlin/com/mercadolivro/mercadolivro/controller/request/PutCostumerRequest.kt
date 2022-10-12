package com.mercadolivro.mercadolivro.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PutCostumerRequest (

    @field:NotEmpty(message = "O campo não deve ser vazio")
    var name : String,

    @field:Email(message = "Email inválido")
    var email: String
        ){
}