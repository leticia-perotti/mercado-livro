package com.mercadolivro.mercadolivro.model

import org.springframework.web.bind.annotation.GetMapping
import javax.persistence.*

@Entity(name = "costumer")
data class CostumerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String
) {

}