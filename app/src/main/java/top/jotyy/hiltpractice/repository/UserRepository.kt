package top.jotyy.hiltpractice.repository

import android.util.Log
import top.jotyy.hiltpractice.data.Failure
import top.jotyy.hiltpractice.data.HttpResult
import top.jotyy.hiltpractice.data.Success
import top.jotyy.hiltpractice.data.model.User
import top.jotyy.hiltpractice.data.remote.ApiService
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun fetchUser(user: String): HttpResult<User> =
        try {
            val result = api.fetchUser(user)
            Log.w("HTTP", result.toString())
            Success(result)
        } catch (e: IOException) {
            Failure(e.message ?: "Network error")
        }
}
