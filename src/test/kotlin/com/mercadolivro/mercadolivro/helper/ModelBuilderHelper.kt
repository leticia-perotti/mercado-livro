package com.mercadolivro.mercadolivro.helper

import com.mercadolivro.mercadolivro.enum.CostumerStatus
import com.mercadolivro.mercadolivro.enum.Role
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.model.PurchaseModel
import java.math.BigDecimal
import java.util.*


fun buildCustomer(
    id: Int? = null,
    name: String = "customer name",
    email: String = "${UUID.randomUUID()}@email.com",
    password: String = "password"
) = CostumerModel(
    id = id,
    name = name,
    email = email,
    status = CostumerStatus.ATIVO,
    password = password,
    roles = setOf(Role.COSTUMER)
)

fun buildPurchase(
    id : Int? = null,
    costumer: CostumerModel = buildCustomer(),
    books : MutableList<BookModel> = mutableListOf(),
    nfe: String? = UUID.randomUUID().toString(),
    price: BigDecimal = BigDecimal.TEN
) = PurchaseModel(
    id = id,
    costumer = costumer,
    books = books,
    nfe = nfe,
    price = price
)
