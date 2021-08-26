package io.github.svs.arrowdemo.examples.e06

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.right
import io.github.svs.arrowdemo.User
import io.github.svs.arrowdemo.UsersRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

class UsersController(
    private val usersRepository: UsersRepository
) {

    @GetMapping("/api/v1/users")
    fun listUsers(@RequestParam("externalId") externalId: String?): ResponseEntity<UsersResponse> {
        return ResponseEntity.ok(externalId.splitOrEmpty().fold({
            UsersResponse(it.flatMap { usersRepository.findUsersByExternalId(it) }, ResponseStatus.PROCESSED)
        }, { it }))
    }
}


fun String?.splitOrEmpty() : Either<List<String>, UsersResponse> {
    return this?.let {
        split(",")
    }?.let {
        Either.Left(it)
    } ?: Either.Right(UsersResponse(listOf(), ResponseStatus.FAILED))
}

data class UsersResponse(
    val list: List<User>,
    val status: ResponseStatus
)
enum class ResponseStatus { PROCESSED, FAILED }