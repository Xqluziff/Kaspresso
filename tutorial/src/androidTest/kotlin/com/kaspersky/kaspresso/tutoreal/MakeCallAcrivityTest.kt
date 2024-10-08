package com.kaspersky.kaspresso.tutoreal

import android.content.Context
import android.media.AudioManager

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.rule.GrantPermissionRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutoreal.screen.MainSceen
import com.kaspersky.kaspresso.tutoreal.screen.MakeCallActivityScreen
import com.kaspersky.kaspresso.tutorial.MainActivity
import com.kaspersky.kaspresso.tutorial.R
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MakeCallAcrivityTest: TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    private val testNumber = "111"

    @Test
    fun checkSuccessCall() = before {
    }.after {
        device.phone.cancelCall(testNumber)
    }.run {

        step("Главный экран"){
            MainSceen{
                makeCallActivityButton{
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }

        step("Отображение UI элементов"){

            MakeCallActivityScreen{
                inputNumber{
                    isVisible()
                    hasHint(R.string.phone_number_hint)
                }

                makeCallButton{
                    isVisible()
                    isClickable()
                    hasText(R.string.make_call_btn)
                }
            }
        }

        step("Ввод номер телефона"){
            MakeCallActivityScreen{
                inputNumber.replaceText(testNumber)
                makeCallButton.click()
            }

        }

        step("Accept permission") {
            device.permissions.apply {
                flakySafely {
                    Assert.assertTrue(isDialogVisible())
                    allowViaDialog()
                }
            }
        }

        step("Проверка звонка"){
            flakySafely {
                val manager = device.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                Assert.assertTrue(manager.mode== AudioManager.MODE_IN_CALL)
            }

        }
    }

    @Test
    fun checkCallIfPermissionDenied() = run {
        step("Open make call activity") {
            MainSceen{
                makeCallActivityButton {
                    isVisible()
                    isClickable()
                    click()
                }
            }


        }
        step("Check UI elements") {
            MakeCallActivityScreen {
                inputNumber.isVisible()
                inputNumber.hasHint(R.string.phone_number_hint)
                makeCallButton.isVisible()
                makeCallButton.isClickable()
                makeCallButton.hasText(R.string.make_call_btn)
            }
        }
        step("Try to call number") {
            MakeCallActivityScreen {
                inputNumber.replaceText(testNumber)
                makeCallButton.click()
            }
        }
        step("Deny permission") {
            device.permissions.apply {
                flakySafely {
                    Assert.assertTrue(isDialogVisible())
                    denyViaDialog()
                }
            }
        }
        step("Check stay on the same screen") {
            MakeCallActivityScreen {
                inputNumber.isDisplayed()
                makeCallButton.isDisplayed()
            }
        }
    }

}
