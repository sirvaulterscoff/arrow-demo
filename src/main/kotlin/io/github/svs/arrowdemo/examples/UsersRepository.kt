package io.github.svs.arrowdemo

interface UsersRepository {
    fun findUsersByExternalId(externalId: String) : List<User>
    fun findUsersByExternalIds(externalId: List<String>) : List<User>
    fun findUsersById(externalId: List<Int>) : List<User>

    fun findUsersByExternalIdsAndLocation(externalId: List<String>, location: Location) : List<User>
    suspend fun findUserById(id: String) : User?
}

data class User(
    val id: Long,
    val externalId: String,
    val fullName: String,
    val location: Location?
) {
    suspend fun location() : Location? = location
}

data class Location(
    val x: Double, val y : Double
)