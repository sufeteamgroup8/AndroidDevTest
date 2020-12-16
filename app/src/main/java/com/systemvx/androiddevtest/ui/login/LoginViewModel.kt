package com.systemvx.androiddevtest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    data class LoginResult(
            val loginSuccess: Boolean? = null
    )

    private var _nameInput = MutableLiveData<String>("")
    val nameInput: LiveData<String> = _nameInput

    private var _passwordInput = MutableLiveData<String>("")
    val passwdInput: LiveData<String> = _passwordInput

    private var _loginResult = MutableLiveData<LoginResult>(LoginResult(null))
    val loginResult: LiveData<LoginResult> = _loginResult

    /**
     * 判断用户名输入是否合法.
     */
    fun isUserNameValid() = !_nameInput.value.isNullOrBlank()

    /**
     * 判断密码格式是否合法.
     */
    fun isPasswordValid() = !_passwordInput.value.isNullOrBlank()

    fun isInputValid() = (isPasswordValid() && isUserNameValid())

    fun changeLoginParams(name: String? ,password:String?){
        name?.also { _nameInput.value = it }
        password?.also { _passwordInput.value = it }
    }

    fun performLogin() {
        val result = loginRepository.login(_nameInput.value!!, _passwordInput.value!!)

        _loginResult.value = LoginResult(result)
    }


}