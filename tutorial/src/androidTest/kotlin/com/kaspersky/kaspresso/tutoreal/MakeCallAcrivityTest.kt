package com.kaspersky.kaspresso.tutoreal

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutoreal.screen.MainSceen
import com.kaspersky.kaspresso.tutoreal.screen.MakeCallActivityScreen
import com.kaspersky.kaspresso.tutorial.MainActivity
import com.kaspersky.kaspresso.tutorial.R
import org.junit.Rule
import org.junit.Test

class MakeCallAcrivityTest: TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkSuccessCall() = run {

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

        step("Провернка звонка"){
            MakeCallActivityScreen{
                inputNumber.replaceText("123")
                makeCallButton.click()
            }

        }
    }

}
