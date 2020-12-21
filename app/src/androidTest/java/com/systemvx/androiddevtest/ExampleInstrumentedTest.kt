package com.systemvx.androiddevtest

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.systemvx.androiddevtest.ui.login.LoginViewModel
import com.systemvx.androiddevtest.ui.login.LoginViewModelFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.systemvx.androiddevtest", appContext.packageName)
    }

    @Test
    fun testRegisterAndLogin() {
        val viewModel: LoginViewModel = LoginViewModelFactory().create(LoginViewModel::class.java)
        viewModel.invokeLoginParams("alyce", "password", "12345678")
        viewModel.register()
        viewModel.performLogin()
    }
}

