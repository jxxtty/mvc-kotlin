package com.example.mvckotlin.controller.delete

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

// 현재 연결된 DB가 없어서 다양한 테스트 불가
@RestController
@RequestMapping("/api")
@Validated // 이걸 작성해줘야 이 컨트롤러 안에있는 @NotNull, @Min 이런애들이 동작하게 된다. -> 자바빈의 유효성을 검증하기 위해서는 age: Int 자체가 빈이 되어야하는데, 그렇지 않으므로 이 어노테이션을 달아 해결해주는 것이다.
class DeleteApiController {

    // 1. path variable
    // 2. request param

    @DeleteMapping("/delete-mapping")
    fun deleteMapping(
        @RequestParam name: String,
        @NotNull(message = "age 값이 누락되었습니다.") @Min(value = 20, message = "age는 20보다 커야합니다.") @RequestParam age: Int): String { // 조건에 맞지 않는 값이 들어오면 500 error 발생
        println(name)
        println(age)

        return name+" "+age
    }

    @DeleteMapping("/delete-mapping/name/{name}/age/{age}")
    fun deleteMappingPath(@Size(min = 2, max = 5) @NotNull(message = "name 값이 누락되었습니다.") @PathVariable name: String, // 500 error - deleteMappingPath.name: 크기가 2에서 5 사이여야 합니다
                          @NotNull(message = "age 값이 누락되었습니다.") @Min(value = 20, message = "age는 20보다 커야합니다.") @PathVariable age: Int): String {
        println(name)
        println(age)

        return name+" "+age
    }
}