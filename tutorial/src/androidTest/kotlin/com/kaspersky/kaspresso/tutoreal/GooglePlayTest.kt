package com.kaspersky.kaspresso.tutoreal

import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutoreal.screen.GooglePlayScreen
import org.junit.Test

class GooglePlayTest : TestCase() {

    @Test
    fun testNotSignIn() = run {

        step("Открытие Google play") {
            val intent = device.targetContext.packageManager.getLaunchIntentForPackage(GOOGLE_PLAY_PACKAGE)
            device.targetContext.startActivity(intent)
        }


        step("Отображение кнопки Sing in"){
            GooglePlayScreen{

                signInButton.isDisplayed()
            }
        }


    }

    companion object {

        private const val GOOGLE_PLAY_PACKAGE = "com.android.vending"
    }
}
