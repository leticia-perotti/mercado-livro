package com.mercadolivro.mercadolivro.repository

import com.mercadolivro.mercadolivro.helper.buildCustomer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CostumerRepositoryClass {

    @Autowired
    private lateinit var costumerRepository: CostumerRepository

    @BeforeEach
    fun setup() = costumerRepository.deleteAll()

    @Test
    fun `should return name containing`(){
        val marcos = costumerRepository.save(buildCustomer(name = "Marcos"))
        val matheus = costumerRepository.save(buildCustomer(name = "Matheus"))
        val alex = costumerRepository.save(buildCustomer(name = "Alex"))

        val costumers = costumerRepository.findByNameContaining("Ma")

        assertEquals(listOf(marcos, matheus), costumers)
    }

    @Nested
    inner class `exists by email`{
        @Test
        fun `should return true when email exists`(){
            val email = "email@teste.com"

            costumerRepository.save(buildCustomer(email = email))

            val exists = costumerRepository.existsByEmail(email)

            assertTrue(exists)
        }

        @Test
        fun `should return false when do not email exists`(){
            val email = "nonexistemail@teste.com"

            val exists = costumerRepository.existsByEmail(email)

            assertFalse(exists)
        }
    }

    @Nested
    inner class `find by email`{
        @Test
        fun `should return costumer when email exists`(){
            val email = "email@teste.com"

            val costumer = costumerRepository.save(buildCustomer(email = email))

            val result = costumerRepository.findByEmail(email)

            assertNotNull(result)
            assertEquals(costumer, result)
        }

        @Test
        fun `should return null when do not email exists`(){
            val email = "nonexistemail@teste.com"

            val returns = costumerRepository.findByEmail(email)

            assertNull(returns)
        }
    }
}