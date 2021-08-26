package io.github.svs.arrowdemo.examples.e04

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
        val response = if (externalId.isPresent) {
            val users = externalId.get()
                .split(",")
                .flatMap {
                    usersRepository.findUsersByExternalId(it)
                }
                UsersResponse(users, ResponseStatus.PROCESSED)
        } else {
            UsersResponse(emptyList(), ResponseStatus.FAILED)
        }
        return ResponseEntity.ok(response)
    }
}

data class UsersResponse(
    val list: List<User>,
    val status: ResponseStatus
)
enum class ResponseStatus { PROCESSED, FAILED }