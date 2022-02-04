package com.example.mvckotlin.controller.exception

import com.example.mvckotlin.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest // Spring의 모든 테스트 기능을 끌고올 필요가 없음. MVC에 대한 테스트만 진행하기 위해 해당 어노테이션을 사용한다
@AutoConfigureMockMvc
class ExceptionApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception/hello2")
        ).andExpect (
            MockMvcResultMatchers.status().isOk
        ).andExpect (
            MockMvcResultMatchers.content().string("hello")
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "jxxtty")
        queryParams.add("age", "20")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception").queryParams(queryParams)
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andExpect(
            MockMvcResultMatchers.content().string("jxxtty : 20")
        ).andDo(MockMvcResultHandlers.print())
    }


    @Test
    fun getFailTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "jxxtty")
        queryParams.add("age", "9")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception").queryParams(queryParams)
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest
        ).andExpect(
            MockMvcResultMatchers.content().contentType("application/json")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.result_code").value("FAIL")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.errors[0].field").value("age")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.errors[0].value").value("9")
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun postTest() {

        val userRequest = UserRequest().apply {
            this.name = "jxxtty"
            this.age = 10
            this.phoneNumber = "010-1111-2222"
            this.address = "서울시"
            this.email = "jxxtty@naver.com"
            this.createdAt = "2022-02-04 13:00:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json")
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.name").value("jxxtty")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.age").value("10")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.phone_number").value("010-1111-2222")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.address").value("서울시")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.email").value("jxxtty@naver.com")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.created_at").value("2022-02-04 13:00:00")
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun postFailTest() {

        val userRequest = UserRequest().apply {
            this.name = "jxxtty"
            this.age = -1
            this.phoneNumber = "010-1111-2222"
            this.address = "서울시"
            this.email = "jxxtty@naver.com"
            this.createdAt = "2022-02-04 13:00:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json")
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest
        ).andExpect(
            MockMvcResultMatchers.content().contentType("application/json")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.result_code").value("FAIL")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.errors[0].field").value("age")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("\$.errors[0].value").value("-1")
        ).andDo(MockMvcResultHandlers.print())
    }
}