package com.mercadolivro.mercadolivro.controller.response

import java.math.BigDecimal

data class LivrosVendaResponse (
    val livro: String,
    val valor: BigDecimal
        )