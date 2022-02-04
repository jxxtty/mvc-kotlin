package com.example.mvckotlin.model.http

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ErrorResponse(
    @JsonProperty(value = "result_code")
    var resultCode: String? = null,

    @field:JsonProperty(value = "http_status")
    var httpStatus: String? = null,

    @JsonProperty(value = "http_method")
    var httpMethod: String? = null,

    var message: String? = null,
    var path: String? = null,
    var timestamp: LocalDateTime? = null,
    var errors: MutableList<Error>? = mutableListOf()
)

data class Error(
    var field: String? = null,
    var messgae: String? = null,
    var value: Any? = null
)