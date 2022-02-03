package com.example.mvckotlin.controller.post

import com.example.mvckotlin.model.http.UserRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PostApiController {

    // @RequestMapping(method = [RequestMethod.POST], path = ["/request-mapping"])
    @PostMapping("/post-mapping")
    fun postMapping(): String {
        return "post-mapping"
    }

    // post 는 body에 데이터를 담아서 받을 수 있다.
    // object mapper를 사용한다. object mapper는 json -> object , object -> json으로 변환해준다.(라이브러리임)
    @PostMapping("/post-mapping/object")
    fun postMappingObject(@RequestBody userRequest: UserRequest): UserRequest {
        // 받아올 때 : json -> object
        println(userRequest)
        return userRequest // 리턴될 때 : object -> json
    }



}