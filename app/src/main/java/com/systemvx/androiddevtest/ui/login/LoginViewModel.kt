package com.systemvx.androiddevtest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    data class LoginResult(
            val loginSuccess: Boolean? = null,
            val registerSuccess: Boolean? = null,
    )

    private var _nameInput = MutableLiveData<String>("")
    var nameInput: LiveData<String> = _nameInput

    private var _passwordInput = MutableLiveData<String>("")
    var passwdInput: LiveData<String> = _passwordInput

    private var _stuNoInput = MutableLiveData<String>("")
    var stuNoInput: LiveData<String> = _stuNoInput

    private var _loginResult = MutableLiveData<LoginResult>(LoginResult(null))
    var loginResult: LiveData<LoginResult> = _loginResult

    /**
     * 判断用户名输入是否合法.
     */
    fun isUserNameValid() = !_nameInput.value.isNullOrBlank()

    /**
     * 判断密码格式是否合法.
     */
    fun isPasswordValid() = _passwordInput.value!!.length >= 4

    fun isInputValid() = (isPasswordValid() && isUserNameValid())

    fun register() {
        Thread {
            when (loginRepository.dataSource.register(_nameInput.value!!, _passwordInput.value!!, _stuNoInput.value!!)) {
                is Result.Success -> _loginResult.postValue(LoginResult(null, true))
                is Result.Error -> _loginResult.postValue(LoginResult(null, false))
            }
        }.start()
    }

    fun changeLoginParams(name: String?, password: String?, stuNo: String?) {
        name?.also { _nameInput.value = it }
        password?.also { _passwordInput.value = it }
        stuNo?.also { _stuNoInput.value = it }
    }

    fun invokeLoginParams(name: String?, password: String?, stuNo: String?) {
        name?.also { _nameInput.postValue(it) }
        password?.also { _passwordInput.postValue(it) }
        stuNo?.also { _stuNoInput.postValue(it) }
    }

    fun performLogin() {
        Thread(Runnable {
            val result = loginRepository.login(_nameInput.value!!, _passwordInput.value!!)

            _loginResult.postValue(LoginResult(result, null))
        }).start()
    }
}