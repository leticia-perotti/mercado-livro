package com.mercadolivro.mercadolivro.model

import com.mercadolivro.mercadolivro.enum.CostumerStatus
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

    @Column
    @Enumerated(EnumType.STRING)
    var status: CostumerStatus,

    @Column
    var password: String

) {
}