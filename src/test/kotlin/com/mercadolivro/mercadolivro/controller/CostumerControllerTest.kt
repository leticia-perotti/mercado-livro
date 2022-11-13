package com.mercadolivro.mercadolivro.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mercadolivro.mercadolivro.controller.request.PostCostumerRequest
import com.mercadolivro.mercadolivro.controller.request.PutCostumerRequest
import com.mercadolivro.mercadolivro.enum.CostumerStatus
import com.mercadolivro.mercadolivro.helper.buildCustomer
import com.mercadolivro.mercadolivro.repository.CostumerRepository
import com.mercadolivro.mercadolivro.security.UserCostumerDetails
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class CostumerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var costumerRepository: CostumerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() = costumerRepository.deleteAll()

    @AfterEach
    fun tearDown() = costumerRepository.deleteAll()

    @Test
    fun `should return all costumers`(){
        val costumer1 = costumerRepository.save(buildCustomer())
        val costumer2 = costumerRepository.save(buildCustomer())

        mockMvc.perform(get("/costumers"))
            .andExpect(status().isOk)
            .andExpect((jsonPath("$.length()").value(2)))
            .andExpect(jsonPath("$[0].id").value(costumer1.id))
            .andExpect(jsonPath("$[0].name").value(costumer1.name))
            .andExpect(jsonPath("$[0].email").value(costumer1.email))
            .andExpect(jsonPath("$[0].status").value(costumer1.status.name))
            .andExpect(jsonPath("$[1].id").value(costumer2.id))
            .andExpect(jsonPath("$[1].name").value(costumer2.name))
            .andExpect(jsonPath("$[1].email").value(costumer2.email))
            .andExpect(jsonPath("$[1].status").value(costumer2.status.name))
    }

    @Test
    fun `should filter all costumers by name when get all`(){
        val costumer1 = costumerRepository.save(buildCustomer(name = "Gustavo"))
        val costumer2 = costumerRepository.save(buildCustomer(name = "Daniel"))

        mockMvc.perform(get("/costumers?name=Gus"))
            .andExpect(status().isOk)
            .andExpect((jsonPath("$.length()").value(1)))
            .andExpect(jsonPath("$[0].id").value(costumer1.id))
            .andExpect(jsonPath("$[0].name").value(costumer1.name))
            .andExpect(jsonPath("$[0].email").value(costumer1.email))
            .andExpect(jsonPath("$[0].status").value(costumer1.status.name))
    }

    @Test
    fun `should create costumer`(){
        val request = PostCostumerRequest("fake name", "${Random.nextInt()}@email.com", "12345")
        mockMvc.perform(post("/costumers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val costumers = costumerRepository.findAll().toList()

        assertEquals(1, costumers.size)
        assertEquals(request.name, costumers[0].name)
        assertEquals(request.email, costumers[0].email)

    }

    @Test
    fun `should throw error when create costumer has inalid information`(){
        val request = PostCostumerRequest("", "${Random.nextInt()}@email.com", "12345")
        mockMvc.perform(post("/costumers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.httpCode").value(422))
            .andExpect(jsonPath("$.message").value("Invalid request"))
            .andExpect(jsonPath("$.internalCode").value("ML-001"))

    }

    @Test
    fun `should get user by id when user has the same id`(){
        val costumer = costumerRepository.save(buildCustomer())

        mockMvc.perform(get("/costumers/${costumer.id}").with(user(UserCostumerDetails(costumer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(costumer.id))
            .andExpect(jsonPath("$.name").value(costumer.name))
            .andExpect(jsonPath("$.email").value(costumer.email))
            .andExpect(jsonPath("$.status").value(costumer.status.name))
    }

    @Test
    fun `should return forbidden when user has different id`(){
        val costumer = costumerRepository.save(buildCustomer())

        mockMvc.perform(get("/costumers/0").with(user(UserCostumerDetails(costumer))))
            .andExpect(status().isForbidden)
            .andExpect(jsonPath("$.httpCode").value(403))
            .andExpect(jsonPath("$.message").value("Access denied"))
            .andExpect(jsonPath("$.internalCode").value("ML-000"))
   }

    @Test
    @WithMockUser(roles = ["ADMIN"])
    fun `should get user by id when user is admin`(){
        val costumer = costumerRepository.save(buildCustomer())

        mockMvc.perform(get("/costumers/${costumer.id}").with(user(UserCostumerDetails(costumer))))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(costumer.id))
            .andExpect(jsonPath("$.name").value(costumer.name))
            .andExpect(jsonPath("$.email").value(costumer.email))
            .andExpect(jsonPath("$.status").value(costumer.status.name))
    }

    @Test
    fun `should update costumer`(){
        val costumer = costumerRepository.save(buildCustomer())

        val request = PutCostumerRequest("Gustavo", "email@email.com")

        mockMvc.perform(put("/costumers/${costumer.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNoContent)

        val costumers = costumerRepository.findAll().toList()

        assertEquals(1, costumers.size)
        assertEquals(request.name, costumers[0].name)
        assertEquals(request.email, costumers[0].email)

    }

    @Test
    fun `should throw error when put costumer has inalid information`(){
        val request = PutCostumerRequest("", "${Random.nextInt()}@email.com")
        mockMvc.perform(put("/costumers/0")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity)
            .andExpect(jsonPath("$.httpCode").value(422))
            .andExpect(jsonPath("$.message").value("Invalid request"))
            .andExpect(jsonPath("$.internalCode").value("ML-001"))

    }

    @Test
    fun `should return not found when update costumer not exists`(){
        val request = PutCostumerRequest("Gustavo", "${Random.nextInt()}@email.com")
        mockMvc.perform(put("/costumers/0")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.httpCode").value(404))
            .andExpect(jsonPath("$.message").value("Costumer [1] not exist"))
            .andExpect(jsonPath("$.internalCode").value("ML-201"))

    }

    @Test
    fun `should delete costumer`(){
       val costumer = costumerRepository.save(buildCustomer())

       mockMvc.perform(delete("/costumers/${costumer.id}"))
           .andExpect(status().isNoContent)

        val costumerDeleted = costumerRepository.findById(costumer.id!!)

        assertEquals(CostumerStatus.INATIVO, costumerDeleted.get().status)
    }

    @Test
    fun `should return not found when delete costumer not exists`(){
        mockMvc.perform(delete("/costumers/1"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.httpCode").value(404))
            .andExpect(jsonPath("$.message").value("Costumer [1] not exist"))
            .andExpect(jsonPath("$.internalCode").value("ML-201"))


    }

}