package io.github.svs.arrowdemo.examples.e07

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.computations.nullable
import arrow.core.right
import io.github.svs.arrowdemo.Location
import io.github.svs.arrowdemo.User
import io.github.svs.arrowdemo.UsersRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

class UsersController(
    private val usersRepository: UsersRepository
) {

    @GetMapping("/api/v1/user/{id}/location")
    suspend fun getUserLocation(@PathVariable("id") id: String?): ResponseEntity<Location> {
        return nullable {
            val _id = id.bind()
            val user = usersRepository.findUserById(_id).bind()
            val location = user.location().bind()
            ResponseEntity.ok(location)
        } ?: ResponseEntity.notFound().build()
    }
}


suspend fun toInt(s: String) = s.toInt()
suspend fun main() {
    val x = "32"
    val r = nullable {
        println("Im in")
        val s = x.bind()
        println("Parsing")
        val x = toInt(s).bind()
        x
    }
    println(r)
}