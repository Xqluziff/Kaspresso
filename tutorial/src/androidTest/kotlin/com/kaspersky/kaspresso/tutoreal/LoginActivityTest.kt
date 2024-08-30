package com.kaspersky.kaspresso.tutoreal

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutoreal.screen.AfterLoginScreen
import com.kaspersky.kaspresso.tutoreal.screen.LoginScreen
import com.kaspersky.kaspresso.tutoreal.screen.MainSceen
import com.kaspersky.kaspresso.tutorial.MainActivity
import com.kaspersky.kaspresso.tutorial.R
import com.kaspersky.kaspresso.tutorial.afterlogin.AfterLoginActivity
import com.kaspersky.kaspresso.tutorial.login.LoginActivity
import org.junit.Rule
import org.junit.Test

class LoginActivityTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test(){
        run{
            step("Авторизация успешная"){
                scenario(LoginScenario(login = "Xqluziff",pass = "123456"))
            }

            step("Проверка экрана успешной авторзации"){
                AfterLoginScreen{
                    title.isVisible()
                    title.hasText(R.string.screen_after_login)
                }

            }
        }
    }
}

