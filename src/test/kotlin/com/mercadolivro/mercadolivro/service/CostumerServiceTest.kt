package com.mercadolivro.service

import com.mercadolivro.mercadolivro.enum.CostumerStatus
import com.mercadolivro.mercadolivro.enum.Role
import com.mercadolivro.mercadolivro.exception.NotFoundException
import com.mercadolivro.mercadolivro.model.CostumerModel
import com.mercadolivro.mercadolivro.repository.CostumerRepository
import com.mercadolivro.mercadolivro.service.BookService
import com.mercadolivro.mercadolivro.service.CostumerService
import io.mockk.*
import io.mockk.impl.annotations.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@ExtendWith(MockKExtension::class)
class CostumerServiceTest {
    @MockK
    private lateinit var customerRepository: CostumerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    private lateinit var customerService: CostumerService

    @Test
    fun `should return all customers`() {
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findAll() } returns fakeCustomers

        val customers = customerService.getAll(null)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 1) { customerRepository.findAll() }
        verify(exactly = 0) { customerRepository.findByNameContaining(any()) }
    }

    @Test
    fun `should return all customers when name is informed`() {
        val name = UUID.randomUUID().toString()
        val fakeCustomers = listOf(buildCustomer(), buildCustomer())

        every { customerRepository.findByNameContaining(name) } returns fakeCustomers

        val customers = customerService.getAll(name)

        assertEquals(fakeCustomers, customers)
        verify(exactly = 0) { customerRepository.findAll() }
        verify(exactly = 1) { customerRepository.findByNameContaining(name) }
    }

    @Test
    fun `fake itens`(){
        assertEquals(2,3)

        verify(exactly = 0){customerRepository.findByNameContaining(("Gustavo"))}
    }

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

    @Test
    fun `should create costumer and encrypt password`(){
        val initialPassword = Random().nextInt().toString()
        val fakeCostumer = buildCustomer(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeCostumerEncrypted = fakeCostumer.copy(password = fakePassword)

        every { customerRepository.save(fakeCostumerEncrypted) } returns fakeCostumer

        every { bCrypt.encode(initialPassword) } returns fakePassword

        customerService.create(fakeCostumer)

        verify(exactly = 1) { customerRepository.save(fakeCostumerEncrypted)}
        verify(exactly = 1) { bCrypt.encode(initialPassword) }
    }

    @Test
    fun `should return costumer by id`(){
        val id = Random().nextInt()
        val fakeCostumer = buildCustomer(id = id)

        every { customerRepository.findById(id) } returns Optional.of(fakeCostumer)

        val costumer = customerService.returnById(id)

        assertEquals(fakeCostumer, costumer)
        verify (exactly = 1){ customerRepository.findById(id) }
    }

    @Test
    fun `should throw error when costumer not found`(){
        val id = Random().nextInt()

        every { customerRepository.findById(id) } returns Optional.empty()

        val error:NotFoundException = assertThrows<NotFoundException>{
            customerService.returnById(id)
        }

        assertEquals("Costumer [${id}] not exists", error.message)
        assertEquals("ML201", error.errorCode)
        
        verify (exactly = 1){ customerRepository.findById(id) }
        


    }
}