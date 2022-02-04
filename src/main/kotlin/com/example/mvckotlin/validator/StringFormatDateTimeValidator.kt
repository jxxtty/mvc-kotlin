package com.example.mvckotlin.validator

import com.example.mvckotlin.annotation.StringFormatDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class StringFormatDateTimeValidator : ConstraintValidator<StringFormatDateTime, String>{ // ConstraintValidator<커스텀 annotation, 입력받는 값 타입>

    private var pattern: String? = null
    override fun initialize(constraintAnnotation: StringFormatDateTime?) {
        this.pattern = constraintAnnotation?.pattern // pattern이 null일 수 있기 때문에 엘비스 연산자를 사용해준다.
    }

    // 검증을 할 때 이 isValid 메소드를 통해서 검증이 된다.(value = 입력되어 들어온값, 검증을 진행해야 하는 값)
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return try{
            LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern))
            true
        } catch (e: Exception) {
            false
        }
    }
}