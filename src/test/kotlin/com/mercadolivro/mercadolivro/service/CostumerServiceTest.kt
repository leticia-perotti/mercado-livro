package com.mercadolivro.service

import com.mercadolivro.mercadolivro.enum.CostumerStatus
import com.mercadolivro.mercadolivro.enum.Errors
import com.mercadolivro.mercadolivro.enum.Role
import com.mercadolivro.mercadolivro.exception.NotFoundException
import com.mercadolivro.mercadolivro.helper.buildCustomer
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
    @SpyK
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
    fun `should throw not found when find by id`(){
        val id = Random().nextInt()

        every { customerRepository.findById(id) } returns Optional.empty()

        val error:NotFoundException = assertThrows<NotFoundException>{
            customerService.returnById(id)
        }

        assertEquals("Costumer [${id}] not exists", error.message)
        assertEquals("ML201", error.errorCode)
        
        verify (exactly = 1){ customerRepository.findById(id) }
    }

    @Test
    fun `should update costumer`(){
        val id = Random().nextInt()
        val fakeCostumer = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns true
        every { customerRepository.save(fakeCostumer) } returns fakeCostumer

        verify (exactly = 1){ customerRepository.existsById(id) }
        verify (exactly = 1){ customerRepository.save(fakeCostumer) }


        customerService.editCostumer(fakeCostumer)
    }

    @Test
    fun `should throw not found exception when update costumer`(){
        val id = Random().nextInt()
        val fakeCostumer = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns false
        every { customerRepository.save(fakeCostumer) } returns fakeCostumer

        val error = assertThrows<NotFoundException> {
            customerService.editCostumer(fakeCostumer)
        }

        assertEquals("Costumer [${id}] not exist", error.message)
        assertEquals("ML-201", error.errorCode)

        verify(exactly = 1) { customerRepository.existsById(id) }
        verify(exactly = 0) { customerRepository.save(any()) }
    }

    @Test
    fun `should delete costumer`(){
        val id = Random().nextInt()
        val fakeCostumer = buildCustomer(id = id)
        val expectedCostumer = fakeCostumer.copy(status = CostumerStatus.INATIVO)

        every { customerService.returnById(id) } returns fakeCostumer
        every { customerRepository.save(expectedCostumer) } returns expectedCostumer
        every { bookService.deleteByCostumer(fakeCostumer) } just runs

        customerService.deleteCostumer(id)

        verify (exactly = 1){ customerService.returnById(id) }
        verify (exactly = 1){ bookService.deleteByCostumer(fakeCostumer) }
        verify (exactly = 1){ customerRepository.save(expectedCostumer) }

    }

    @Test
    fun `should throw not found exception when delete costumer `(){
        val id = Random().nextInt()

        every { customerService.returnById(id) } throws NotFoundException(Errors.ML201.message.format(id),
                                                                          Errors.ML201.code)

        val error = assertThrows<NotFoundException> {
            customerService.deleteCostumer(id)
        }

        assertEquals("Costumer [${id}] not exist", error.message)
        assertEquals("ML-201", error.errorCode)

        verify (exactly = 1){ customerService.returnById(id) }
        verify (exactly = 0){ bookService.deleteByCostumer(any()) }
        verify (exactly = 0){ customerRepository.save(any()) }

    }

    @Test
    fun `should return true when email availeble`(){
        val email = "${Random().nextInt().toString()}@email.com"

        every { customerRepository.existsByEmail(email) } returns false

        val emailAvailable = customerService.emailAvailable(email)

        assertTrue(emailAvailable)

        verify(exactly = 1) { customerRepository.existsByEmail(email) }
    }

    @Test
    fun `should return false when email availeble`(){
        val email = "${Random().nextInt()}@email.com"

        every { customerRepository.existsByEmail(email) } returns true

        val emailAvailable = customerService.emailAvailable(email)

        assertFalse(emailAvailable)

        verify(exactly = 1) { customerRepository.existsByEmail(email) }
    }
}