package com.mercadolivro.mercadolivro.service

import com.mercadolivro.mercadolivro.event.PurchaseEvent
import com.mercadolivro.mercadolivro.helper.buildCustomer
import com.mercadolivro.mercadolivro.helper.buildPurchase
import com.mercadolivro.mercadolivro.model.BookModel
import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.model.PurchaseModel
import com.mercadolivro.mercadolivro.repository.BookRepository
import com.mercadolivro.mercadolivro.repository.PurchaseRespository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
class PurchaseServiceTest{

    @MockK
    private lateinit var purchaseRespository: PurchaseRespository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    @MockK
    private lateinit var bookRepository: BookRepository


    @InjectMockKs
    private lateinit var purchaseService: PurchaseService

    val purchaseEventSlot = slot<PurchaseEvent>()

    @Test
    fun `should create purchase and publish event`(){
        val purchase = buildPurchase()

        every { purchaseRespository.save(purchase) } returns purchase
        every { applicationEventPublisher.publishEvent(any()) } just runs

        purchaseService.create(purchase)

        verify(exactly = 1) { purchaseRespository.save(purchase) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(
            capture(purchaseEventSlot)
        ) }

        assertEquals(purchase, purchaseEventSlot.captured.purchaseModel)
    }

    @Test
    fun `should update purchase`(){
        val purchase = buildPurchase()

        every { purchaseRespository.save(purchase) } returns purchase

        purchaseService.update(purchase)

        verify (exactly = 1){ purchaseRespository.save(purchase) }
    }
}