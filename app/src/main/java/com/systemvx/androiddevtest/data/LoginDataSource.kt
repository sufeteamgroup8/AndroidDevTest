package com.systemvx.androiddevtest.data

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<User> {
        return try {
            // TODO: handle loggedInUser authentication
            val fakeUser = User(1, "alyce", "ALYCE", "Hello world by alice")
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(Exception("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}