package com.example.mvckotlin.controller.delete

import org.springframework.web.bind.annotation.*

// 현재 연결된 DB가 없어서 다양한 테스트 불가
@RestController
@RequestMapping("/api")
class DeleteApiController {

    // 1. path variable
    // 2. request param

    @DeleteMapping("/delete-mapping")
    fun deleteMapping(@RequestParam name: String, @RequestParam age: Int): String {
        println(name)
        println(age)

        return name+" "+age
    }

    @DeleteMapping("/delete-mapping/name/{name}/age/{age}")
    fun deleteMappingPath(@PathVariable name: String, @PathVariable age: Int): String {
        println(name)
        println(age)

        return name+" "+age
    }
}