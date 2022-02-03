package com.example.mvckotlin.controller.response

import com.example.mvckotlin.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/response")
class ResponseApiController {

    // 1. get -> 4xx
    @GetMapping("")
    fun getMapping(@RequestParam age: Int?): ResponseEntity<String> { // @ReqeustParam의 required 기본값은 true => 값이 들어올 수도 있고 안들어올 수도 있다면 required false처리 하거나 ?를 붙여서 null이 들어올 수 있다고 알려준다.
//        // 0. age == null -> 400 error
//        if(age == null) {
//            return ResponseEntity.status(400).body("age값이 누락되었습니다.")
//        }
//
//        // 1. age는 반드시 20살 이상이어야 한다 (age >= 20) -> 400 error
//        if(age < 20) {
//            return ResponseEntity.status(400).body("age값은 20이상이어야 합니다.")
//        }
//
//        return ResponseEntity.ok("ok")

        return age?.let {
            // age not null

            if(age < 20) {
                return ResponseEntity.status(400).body("age값은 20이상이어야 합니다.")
            }

            ResponseEntity.ok("ok")

        }?: run {
            // age is null
            return ResponseEntity.status(400).body("age값이 누락되었습니다.")
        }
    }

    // 2. post -> 200
    @PostMapping("")
    fun postMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<Any> {
        return ResponseEntity.status(200).body(userRequest)
    }

    // 3. put -> 201
    @PutMapping("")
    fun putMapping(@RequestBody userRequest: UserRequest?): ResponseEntity<UserRequest> {
        // 1. 기존 데이터가 없어서 새로 생성
        return ResponseEntity.status(HttpStatus.CREATED).body(userRequest)
    }


    // 4. delete -> 500
    @DeleteMapping("/{id}")
    fun deleteMapping(@PathVariable id: Int): ResponseEntity<Any> {
        return ResponseEntity.status(500).body(null)
    }
}