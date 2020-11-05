package top.jotyy.hiltpractice

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import top.jotyy.hiltpractice.data.Failure
import top.jotyy.hiltpractice.data.HttpResult
import top.jotyy.hiltpractice.data.State
import top.jotyy.hiltpractice.data.Success
import top.jotyy.hiltpractice.data.model.User
import top.jotyy.hiltpractice.repository.UserRepository

class MainViewModel @ViewModelInject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    private val _error = MutableLiveData<String>()
    private val _state = MutableLiveData<State>()

    val user: LiveData<User> get() = _user
    val error: LiveData<String> get() = _error
    val state: LiveData<State> get() = _state

    init {
        fetchUser()
    }

    fun fetchUser() {
        viewModelScope.launch {
            userRepository.fetchUser("jotyy").collect { result ->
                when (result) {
                    is Success -> _user.value = result.data
                    is Failure -> _error.value = result.error
                    is State.Loading -> _state.value = result
                    is State.Loaded -> _state.value = result
                }
            }
        }
    }
}
