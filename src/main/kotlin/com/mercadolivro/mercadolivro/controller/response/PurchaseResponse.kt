package com.mercadolivro.mercadolivro.controller.response

import java.math.BigDecimal

data class PurchaseResponse(
    val cliente:String,
    val valorTotal: BigDecimal,
    val listaLivros: List<LivrosVendaResponse>
) {
}