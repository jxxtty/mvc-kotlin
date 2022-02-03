package com.example.mvckotlin.controller.put

import com.example.mvckotlin.model.http.Result
import com.example.mvckotlin.model.http.UserRequest
import com.example.mvckotlin.model.http.UserResponse
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PutApiController {

    @PutMapping("/put-mapping") // @RequestMapping(method = [RequestMethod.PUT], path = ["/put-mapping"])
    fun putMapping(): String{
        return "put-mapping"
    }

    @PutMapping("/put-mapping/object")
    fun putMappingObject(@RequestBody userRequest: UserRequest): UserResponse {
        // 0. response
        return UserResponse().apply {
            // 1. result
            this.result = Result().apply {
                this.resultCode = "OK"
                this.resultMessage = "성공"
            }
        }. apply {
            // 2. description
            this.description = "걀걀걀"
        }.apply {
            // 3. user(mutable List)
            val userList = mutableListOf<UserRequest>()

            userList.add(userRequest)
            userList.add(UserRequest().apply {
                this.name = "a"
                this.age = 10
                this.email = "a@gmail.com"
                this.address = "a Address"
                this.phoneNumber = "010-1111-1111"
            })
            userList.add(UserRequest().apply {
                this.name = "b"
                this.age = 20
                this.email = "b@gmail.com"
                this.address = "b Address"
                this.phoneNumber = "010-2222-2222"
            })

            this.userRequest = userList
        }

    }


}