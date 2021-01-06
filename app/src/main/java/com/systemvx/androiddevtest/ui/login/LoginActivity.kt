package com.systemvx.androiddevtest.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.ActivityLoginBinding
import com.systemvx.androiddevtest.ui.main.MainActivity


class LoginActivity : AppCompatActivity() {
    //持有数据结构的Model层引用
    private lateinit var loginViewModel: LoginViewModel

    //绑定对象,持有所有界面元素
    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //使用DataBinding绑定布局文件
        mBinding = DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)

        //获取数据结构Model
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
                .get(LoginViewModel::class.java)


        /*监听输入框的实时变化*/
        mBinding.username.afterTextChanged {
            loginViewModel.changeLoginParams(it, null, null)
        }
        mBinding.password.afterTextChanged {
            loginViewModel.changeLoginParams(null, it, null)
        }
        mBinding.textInputStudentNo.afterTextChanged {
            loginViewModel.changeLoginParams(null, null, it)
        }

        mBinding.activity = this
        mBinding.studentNo
        // 绑定观察,当@nameInput的data变化时,执行内部动作:
        loginViewModel.nameInput.observe(this@LoginActivity, Observer {
            // disable login button unless both username / password is valid
            mBinding.loginBtn.isEnabled = loginViewModel.isInputValid()

            if (!loginViewModel.isUserNameValid()) {
                mBinding.username.error = getString(R.string.invalid_username)
            }
        })
        // 绑定观察,当@passwdInput的data变化时,执行内部动作:
        loginViewModel.passwdInput.observe(this@LoginActivity, Observer {
            // disable login button unless both username / password is valid
            mBinding.loginBtn.isEnabled = loginViewModel.isInputValid()

            if (!loginViewModel.isPasswordValid()) {
                mBinding.password.error = getString(R.string.invalid_password)
            }
        })

        //登录的结果会使@loginResult的内容变更
        //这里注册监听@loginResult,在数据变更时根据新的内容作出反应
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            mBinding.loading.visibility = View.GONE
            when(loginResult.loginSuccess){
                true ->{
                    updateUiWithUser()
                    setResult(Activity.RESULT_OK)
                    //Complete and destroy login activity once successful
                    finish()
                }
                false ->{
                    showLoginFailed(R.string.login_failed)
                }
                null ->{}
            }
            loginResult.registerSuccess?.let{
                showRegisterStatus(loginResult.registerSuccess)
            }

        })
    }

    private fun showRegisterStatus(registerSuccess: Boolean) {
        when (registerSuccess) {
            true -> {
                Toast.makeText(this, "注册成功,正在登录", Toast.LENGTH_LONG).show()
                loginViewModel.performLogin()
            }
            false -> Toast.makeText(this, "提交失败", Toast.LENGTH_LONG).show()
        }
    }

    /*回调函数区*/
    fun login() {
        mBinding.loading.visibility = ProgressBar.VISIBLE
        when (mBinding.textInputStudentNo.visibility) {
            View.GONE -> {
                loginViewModel.performLogin()
            }
            View.VISIBLE -> {
                loginViewModel.register()
                Toast.makeText(this, "Reg", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun showData() {
        when (mBinding.textInputStudentNo.visibility) {
            View.GONE -> {
                mBinding.textInputStudentNo.visibility = View.VISIBLE
                mBinding.loginBtn.text = getString(R.string.action_reg)
                mBinding.registerBtn.text = getString(R.string.back_to_login)
            }
            View.VISIBLE -> {
                mBinding.textInputStudentNo.visibility = View.GONE
                mBinding.loginBtn.text = getString(R.string.action_sign_in_short)
                mBinding.registerBtn.text = getString(R.string.action_register)
            }
        }
        val inputText = "Name:${loginViewModel.nameInput.value},Passwd:${loginViewModel.passwdInput.value}"
        Toast.makeText(this, inputText, Toast.LENGTH_LONG).show()
    }


    /*内部函数区*/
    private fun updateUiWithUser() {
        val welcome = getString(R.string.welcome)
        val displayName = ""
        // TODO : initiate successful logged in experience
        Toast.makeText(
                applicationContext,
                "$welcome$displayName",
                Toast.LENGTH_LONG
        ).show()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show()
    }

    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}

