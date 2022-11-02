package com.mercadolivro.mercadolivro.model

import com.mercadolivro.mercadolivro.enum.CostumerStatus
import com.mercadolivro.mercadolivro.enum.Role
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
    var password: String,

    @Column(name ="role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "costumer_roles",
        joinColumns = [JoinColumn(name = "costumer_id")])
    val roles: Set<Role> = setOf()
) {
}