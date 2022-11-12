package com.mercadolivro.mercadolivro.controller

import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.controller.request.PutCostumerRequest
import com.mercadolivro.mercadolivro.controller.response.CostumerResponse
import com.mercadolivro.mercadolivro.extension.toCostumerModel
import com.mercadolivro.mercadolivro.extension.toResponse
import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.security.UserCanOnlyAcessThierOwnResource
import com.mercadolivro.mercadolivro.service.CostumerService
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustumerController(
    private val costumerService: CostumerService
    ) {

    @GetMapping
    fun getAll(
        @RequestParam name: String?
    ): List<CostumerResponse> {
        return costumerService.getAll(name).map { it.toResponse() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crateCostumer(
        @RequestBody @Valid costumer: PostCostumerRequest
    ) {
        costumerService.create(costumer.toCostumerModel())
    }

    @GetMapping("/{id}")
    @UserCanOnlyAcessThierOwnResource
    fun getCostumer(
        @PathVariable id: Int
    ): CostumerResponse{
        return costumerService.returnById(id).toResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun putCostumer(
        @PathVariable id: Int,
        @RequestBody @Valid costumer: PutCostumerRequest
    ){
        val costumerSaved = costumerService.returnById(id)
        costumerService.editCostumer(costumer.toCostumerModel(costumerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCostumer(
        @PathVariable id: Int
    ){
        costumerService.deleteCostumer(id)
    }

}