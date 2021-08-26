package io.github.svs.arrowdemo.examples.e03

import io.github.svs.arrowdemo.User
import io.github.svs.arrowdemo.UsersRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

class UsersController(
    private val usersRepository: UsersRepository
) {

    @GetMapping("/api/v1/users")
    fun listUsers(@RequestParam("externalId") externalId: String?) : ResponseEntity<UsersResponse> {
        val response =  externalId?.let {
            it.split(",")
        }?.flatMap {
            usersRepository.findUsersByExternalId(it)
        }?.let {
            UsersResponse(it, ResponseStatus.PROCESSED)
        } ?: UsersResponse(emptyList(), ResponseStatus.FAILED)

        return ResponseEntity.ok(response)
    }
}

data class UsersResponse(
    val list: List<User>,
    val status: ResponseStatus
)
enum class ResponseStatus { PROCESSED, FAILED }