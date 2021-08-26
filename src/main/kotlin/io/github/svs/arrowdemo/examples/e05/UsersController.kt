package io.github.svs.arrowdemo.examples.e05

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
    fun listUsers(@RequestParam("externalId") externalId: Optional<String>) : ResponseEntity<UsersResponse> {
        return externalId.map {
            it.split(",")
        }.map { listOfIds ->
            listOfIds.flatMap { id ->
                usersRepository.findUsersByExternalId(id)
            }
        }.map { users ->
            UsersResponse(users, ResponseStatus.PROCESSED)
        }.or {
            Optional.of(UsersResponse(emptyList(), ResponseStatus.FAILED))
        }.map {
            ResponseEntity.ok(it)
        }.get()
    }
}

data class UsersResponse(
    val list: List<User>,
    val status: ResponseStatus
)
enum class ResponseStatus { PROCESSED, FAILED }