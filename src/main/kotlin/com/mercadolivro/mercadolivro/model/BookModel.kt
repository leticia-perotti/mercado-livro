package com.mercadolivro.mercadolivro.model

import com.mercadolivro.mercadolivro.enum.BookStatus
import lombok.NoArgsConstructor
import java.math.BigDecimal
import javax.persistence.*
import javax.persistence.Id

@Entity
@Table(name = "book")
data class BookModel (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null,

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    var costumerId: CostumerModel? = null


) {
}

