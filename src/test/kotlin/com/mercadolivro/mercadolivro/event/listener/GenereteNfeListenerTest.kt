package com.mercadolivro.mercadolivro.event.listener

import com.mercadolivro.mercadolivro.event.PurchaseEvent
import com.mercadolivro.mercadolivro.helper.buildPurchase
import com.mercadolivro.mercadolivro.service.PurchaseService
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class GenereteNfeListenerTest{

    @MockK
    private lateinit var purchaseService: PurchaseService

    @InjectMockKs
    private lateinit var genereteNfeListener: GenereteNfeListener

    @Test
    fun `should generate nfe`(){
        val purchase = buildPurchase(nfe = null)
        val fakeNfe = UUID.randomUUID()
        val purchaseExpected = purchase.copy(nfe = fakeNfe.toString())
        mockkStatic(UUID::class)

        every { UUID.randomUUID() } returns fakeNfe
        every { purchaseService.update(purchaseExpected) } just runs

        genereteNfeListener.listen(PurchaseEvent(this, purchase))


        verify (exactly = 1){ purchaseService.update(purchaseExpected) }




    }

}