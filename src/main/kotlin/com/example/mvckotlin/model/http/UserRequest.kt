package com.example.mvckotlin.model.http

import com.example.mvckotlin.annotation.StringFormatDateTime
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class) // 클래스 단위에 써주면 JsonProperty를 하나하나 설정해주지 않아도 된다.
data class UserRequest(
    @field:Size(min = 2, max = 8)
    @field:NotEmpty
    var name: String? = null,

    @field:PositiveOrZero // 0 이상의 숫자인지를 검증, 0도 포함한다.(양수인지 확인)
    var age: Int? = null,

    @field:Email
    var email: String? = null,

    @field:NotBlank
    var address: String? = null,


    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    //@JsonProperty("phone_number") // object mapper에게 json naming은 이런이름으로 들어올거야 라고 알려주는 것
    var phoneNumber: String? = null,

    @field:StringFormatDateTime(pattern = "yyyy-MM-dd HH:mm:ss", message = "패턴이 올바르지 않습니다.")
    var createdAt: String? = null
) {
//    @AssertTrue(message = "생성일자의 패턴은 yyyy-MM-dd HH:mm:ss 여야 합니다") // 얘를 달아줘야 validation 을 처리할 때 이 메소드를 참고해서 처리한다
//    private fun isValidCreateAt(): Boolean { // 정상이면 true, 비정상이면 false
//        return try{
//            LocalDateTime.parse(this.createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }
}
