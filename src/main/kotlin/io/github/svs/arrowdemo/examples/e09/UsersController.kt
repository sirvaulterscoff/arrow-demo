package io.github.svs.arrowdemo.examples.e09

import arrow.core.*
import arrow.core.computations.either
import arrow.core.computations.nullable
import arrow.typeclasses.Monoid
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

    @GetMapping("/api/v1/users")
    fun getUsers(@RequestParam("id") id: String?): ResponseEntity<List<User>> {
        val find =   usersRepository::findUsersById compose String::toListOfInts
        return Option.lift(find).partially1(Option.fromNullable(id)).invoke().toReponseEntity()
    }
}

fun  String.toListOfInts() = this.splitByComa().map(String::asInt)

fun String.asInt() = this.toInt()

fun String.splitByComa() = this.split(",")

fun Option<List<User>>.toReponseEntity() : ResponseEntity<List<User>> {
    return this.fold({
        ResponseEntity.notFound().build()
    }, {
        ResponseEntity.ok(it)
    })
}