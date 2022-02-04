package com.example.mvckotlin.controller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// 실제로 이런 컨트롤러는 사용하지 않음
@RestController
@RequestMapping("/api/exception")
class ExceptionApiController {

    @GetMapping("/hello")
    fun hello() {
        if(true){
            throw RuntimeException("강제 Exception 발생")
        }
    }

    @GetMapping("/hello2")
    fun hello2() {
        val list = mutableListOf<String>()
        var temp = list[0]
    }

    // controller 내부에 @ExceptionHandler가 있으면, 얘를 탄다. (GlobalControllerAdvice를 타는게 아니라)
    // 해당 예외가 이 컨트롤러 안에서만 일어난다면 이렇게 컨트롤러 안에서 처리해주고, 모든 컨트롤러에서 일어날 수 있는 예외라면 Global하게 처리해주면 된다.
    // +) 모든 컨트롤러에서 일어날 수 있는 예외이나 응답값 형태가 다 다르다면 이렇게 컨트롤러 안에 각각 선언해주는 것이 좋다.
    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ExceptionApiController - Index Error")
    }
}