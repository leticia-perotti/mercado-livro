package com.mercadolivro.mercadolivro.model

import javax.persistence.*

@Entity
@Table(name = "costumer")
data class CostumerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var email: String,

) {
}