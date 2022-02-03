package com.example.mvckotlin.model.http

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class) // 클래스 단위에 써주면 JsonProperty를 하나하나 설정해주지 않아도 된다.
data class UserRequest(
    var name: String? = null,
    var age: Int? = null,
    var email: String? = null,
    var address: String? = null,

    //@JsonProperty("phone_number") // object mapper에게 json naming은 이런이름으로 들어올거야 라고 알려주는 것
    var phoneNumber: String? = null
)
