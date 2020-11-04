package top.jotyy.hiltpractice.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import top.jotyy.hiltpractice.data.model.User

interface ApiService {

    @GET("users/{user}")
    suspend fun fetchUser(@Path("user") user: String): User
}
