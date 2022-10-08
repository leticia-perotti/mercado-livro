package com.mercadolivro.mercadolivro.model

import com.mercadolivro.mercadolivro.enum.BookStatus
import com.mercadolivro.mercadolivro.enum.Errors
import com.mercadolivro.mercadolivro.exception.BadRequestException
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

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    var costumerId: CostumerModel? = null


){
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set (value){
            if (field == BookStatus.CANCELADO || field == BookStatus.DELETADO)
                throw BadRequestException(Errors.ML102.message.format(field), Errors.ML102.code)

            field = value
        }
    constructor(id: Int? = null, name: String,
                price: BigDecimal, costumerId: CostumerModel? = null,
                status: BookStatus?):this(id, name, price, costumerId){
                    this.status = status
                }
}

