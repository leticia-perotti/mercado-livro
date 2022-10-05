package com.mercadolivro.mercadolivro.model

import org.springframework.web.bind.annotation.GetMapping
import javax.persistence.*
import lombok.NoArgsConstructor;

@Entity
@Table(name = "costumer")
@NoArgsConstructor
data class CostumerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    //@Column
    var name: String,

   // @Column
    var email: String,

) {


}