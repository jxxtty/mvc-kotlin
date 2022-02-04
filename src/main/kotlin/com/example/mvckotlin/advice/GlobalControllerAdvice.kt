package com.example.mvckotlin.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice // RestController에서 발생하는 exception들이 모두 여기서 처리된다
// @RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])   이렇게 쓰면 ExceptionApiController에 있는 예외만 잡아서 처리한다
// 위에꺼처럼 명시해서 사용하는 것이 좋다.
class GlobalControllerAdvice {

    // 여기를 통해 Exception이 처리되게 되면 200 Ok로 응답이 떨어진다.
    // 만약 RestController내에서 각각 throw로 exception을 던지면 500 error로 응답이 떨어진다.
    @ExceptionHandler(value = [RuntimeException::class])
    fun exception(e: RuntimeException): String {
        return "Server Error"
    }

//    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
//    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): String {
//        return "Index Error"
//    }

    // 200 Ok가 아니라 500으로 떨어지게 하려면,
    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBoundsException(e: IndexOutOfBoundsException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Error")
    }


    // @ExceptionHandler(value = [Exception::class])  이렇게 작성하면 발생하는 모든 예외를 여기서 잡아서 처리할 수 있다.
}