package com.mercadolivro.mercadolivro.controller.response

class PageResponse<T> (
    var itens: List<T>,
    var currentPage: Int,
    var totalPages: Long,
    var totalItens: Int,
        ){

}