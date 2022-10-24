package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.mapper.PurchaseMapper
import com.mercadolivro.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.mercadolivro.controller.response.LivrosVendaResponse
import com.mercadolivro.mercadolivro.controller.response.PurchaseResponse
import com.mercadolivro.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController("/purchase")
class PurchaseController (
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
        ){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase (@RequestBody request: PostPurchaseRequest){
        purchaseService.create(purchaseMapper.toModel(request))
    }

    //VALIDAR LIVROS VENDIDOS E N VENDE-LOS NOVAMENTE

    //LISTAR VENDAR
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun listPurchase(): List<PurchaseResponse> {
        val vendas = purchaseService.listAll()
        return vendas.map { purchaseMapper.toResponse(it) }
    }

    //RETORNAR VENDA POR ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun returnPurchaseById(
        @PathVariable id: Int
    ): PurchaseResponse {
        val venda = purchaseService.findById(id)
        return purchaseMapper.toResponse(venda)
    }

    @GetMapping("livros/vendidos")
    @ResponseStatus(HttpStatus.OK)
    fun returnSoldBooks(): List<LivrosVendaResponse> {
        val livrosVendidos = purchaseService.findSoldBooks()
        return livrosVendidos.map {  purchaseMapper.toResponseBook(it) }
    }
}