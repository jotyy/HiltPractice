package top.jotyy.hiltpractice.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import top.jotyy.hiltpractice.data.Failure
import top.jotyy.hiltpractice.data.HttpResult
import top.jotyy.hiltpractice.data.State
import top.jotyy.hiltpractice.data.Success
import top.jotyy.hiltpractice.data.local.UserDao
import top.jotyy.hiltpractice.data.model.User
import top.jotyy.hiltpractice.data.remote.UserService
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao
) {

    suspend fun fetchUser(name: String) =
        flow {
            emit(State.Loading)

            val apiResponse = userService.fetchUserInfo(name)
            val user = apiResponse.body()

            if (apiResponse.isSuccessful && user != null) {
                userDao.insertUser(user)
            } else {
                emit(Failure(apiResponse.message()))
            }

            emit(Success(userDao.getUserByName(name)))

            emit(State.Loaded)
        }
            .catch {
                emit(Failure(it.message ?: "fetch user failed"))
            }
            .flowOn(Dispatchers.IO)
}
