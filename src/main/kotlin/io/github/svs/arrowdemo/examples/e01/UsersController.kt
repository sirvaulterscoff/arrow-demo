package io.github.svs.arrowdemo.examples.e01

import io.github.svs.arrowdemo.User
import io.github.svs.arrowdemo.UsersRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

class UsersController(
    private val usersRepository: UsersRepository
) {

    @GetMapping("/api/v1/users")
    fun listUsers(@RequestParam("externalId") externalId: String?) : ResponseEntity<List<User>> {
        return externalId?.let {
            ResponseEntity.ok(usersRepository.findUsersByExternalId(externalId))
        } ?: ResponseEntity.badRequest().build()
    }
}