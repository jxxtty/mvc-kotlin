package com.example.mvckotlin.controller.get

import com.example.mvckotlin.model.http.UserRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class GetApiController {

    @GetMapping(path = ["/hello", "/abcd"]) // 2개 이상의 주소를 가질 수 있다.
    fun hello(): String {
        return "hello Kotlin"
    }

    @RequestMapping(method = [RequestMethod.GET], path = ["/request-mapping"]) // 옛날 방식
    fun requestMapping(): String {
        return "request-mapping"
    }

    @GetMapping("/get-mapping/path-variable/{name}")
    fun pathVariable(@PathVariable name: String): String{
        println(name)
        return name
    }

    @GetMapping("/get-mapping/path-variable/{name}/{age}")
    fun pathVariable2(@PathVariable name: String, @PathVariable age: Int): String{
        println("이름은 $name, 나이는 $age")
        return name + "나이는 " + age
    }

    @GetMapping("/get-mapping/path-variable/{name}/{age}/2")
    fun pathVariable3(@PathVariable name: String, @PathVariable age: Int): String{ // 이를 수정하기 위해 name으로 받지말고 _name으로 받는다. 단, @PathVariable 속성 중 value나 name에 "name"을 지정해줘야한다.
        val name = "kotlin"
        println("name 이 2개일때!")
        println("이름은 $name, 나이는 $age") // 이름은 kotlin, 나이는 29
        return name + " 나이는 " + age
    }

    @GetMapping("/get-mapping/path-variable/{name}/{age}/3")
    fun pathVariable4(@PathVariable(value = "name") _name: String, @PathVariable age: Int): String{ // 이를 수정하기 위해 name으로 받지말고 _name으로 받는다. 단, @PathVariable 속성 중 value나 name에 "name"을 지정해줘야한다.
        val name = "kotlin"
        println("name 이 2개일때!")
        println("이름은 $_name, 나이는 $age") // 이름은 jxxtty, 나이는 29
        println("이름은 $name, 나이는 $age") // 이름은 kotlin, 나이는 29

        return _name + " 나이는 " + age
    }

    @GetMapping("/get-mapping/query-parameter")
    fun queryParameter(@RequestParam name: String, @RequestParam age: Int): String{ // @RequestParam(name = "name"), @RequestParam(value = "age")
        println("이름은 $name, 나이는 $age") // 이름은 jxxtty, 나이는 29
        return name + " 나이는 " + age
    }

    // name, age, address, email 4가지 정보를 requestParam으로 받으려면? -> 객체로 받는다
    // localhost:8080/api/get-mapping/query-parameter/object?name=jxxtty&age=29&email=jxxtty@gmail.com&address=서울시
    // kotlin에서는 변수명에 하이픈(-)을 사용할 수 없다. 그러나 uri 설계에서는 대문자를 사용하지 않는다.
    // 만약 phoneNumber로 선언된 변수에 phone-number로 받고싶다면 @RequestParam의 name이나 value속성을 이용해서 받아야 한다.(객체로 한번에 받기 불가)
    @GetMapping("/get-mapping/query-parameter/object")
    fun queryParameterObject(userRequest: UserRequest): UserRequest { // RestController에서 리턴타입이 Object라면 JSON형태로 바뀐다
        println(userRequest) // UserRequest(name=jxxtty, age=29, email=jxxtty@gmail.com, address=서울시)
        return userRequest
    }

    // localhost:8080/api/get-mapping/query-parameter/map?name=jxxtty&age=29&email=jxxtty@gmail.com&address=서울시&phone-number=01012341234
    @GetMapping("/get-mapping/query-parameter/map")
    fun queryParameterMap(@RequestParam map: Map<String, Any>): Map<String, Any> {
        println(map) // {name=jxxtty, age=29, email=jxxtty@gmail.com, address=서울시, phone-number=01012341234} -> 여기서 age, phone-number는 string으로 들어온다.
        return map
    }
}