package com.example.mvckotlin.controller.exception

import com.example.mvckotlin.model.http.Error
import com.example.mvckotlin.model.http.ErrorResponse
import com.example.mvckotlin.model.http.UserRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

// 실제로 이런 컨트롤러는 사용하지 않음
@RestController
@RequestMapping("/api/exception")
@Validated
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



    @GetMapping("")
    fun get(
        @NotBlank @Size(min = 2, max = 6) @RequestParam name: String,
        @Min(10) @RequestParam age: Int
    ): String {
        println("name : $name, age: $age")
        return name + " : " + age
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(e: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        // 1. 에러 분석
        val errors = mutableListOf<Error>()

        e.constraintViolations.forEach {
            val error = Error().apply {
                this.field = it.propertyPath.last().name // propertyPath가 배열인데, 제일 마지막에 들어가는게 변수의 이름이다.
                this.messgae = it.message
                this.value = it.invalidValue
            }
            errors.add(error)
        }

        // 2. ErrorResponse
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생하였습니다."
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        // 3. ResponseEntity에 담아 내린다
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }


    @PostMapping("")
    fun post(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class]) // 글로벌하게 처리해줘도 된다. - 이걸로 잡히는건 내려주는형태가 거의 동일하기 때문에
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()

        e.bindingResult.allErrors.forEach { errorObject ->
            val error = Error().apply {
                this.field = (errorObject as FieldError).field
                this.messgae = errorObject.defaultMessage // it을 사용하게되면 위에서 형변환을 해버리기때문에 문제가있어서 errorObject로 받아서 따로 설정해줬다
                this.value = errorObject.rejectedValue
            }

            errors.add(error)
        }

        // 2. ErrorResponse
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "FAIL"
            this.httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생하였습니다."
            this.path = request.requestURI.toString()
            this.timestamp = LocalDateTime.now()
            this.errors = errors
        }

        // 3. ResponseEntity에 담아 내린다
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)

    }
}