package com.kaspersky.kaspresso.tutoreal

import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.kaspersky.kaspresso.tutoreal.screen.LoginScreen
import com.kaspersky.kaspresso.tutoreal.screen.MainSceen
import com.kaspersky.kaspresso.tutorial.R

class LoginScenario(
    private val login : String,
    private val pass : String
) :Scenario(){

    override val steps: TestContext<Unit>.() -> Unit = {
        step("Открытие Главного экрана"){
            MainSceen{

                loginActivityButton{
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }

        step("Проверка отображения элементов"){
            LoginScreen{
                inputUserName{
                    isVisible()
                    hasHint(R.string.login_activity_hint_username)
                }

                inputPassword{
                    isVisible()
                    hasHint(R.string.login_activity_hint_password)
                }

                logginButton{
                    isVisible()
                    isClickable()
                }
            }
        }

        step("Авторизация"){

            LoginScreen{
                inputUserName.replaceText(login)
                inputPassword.replaceText(pass)
                logginButton.click()
            }


        }
    }
}
