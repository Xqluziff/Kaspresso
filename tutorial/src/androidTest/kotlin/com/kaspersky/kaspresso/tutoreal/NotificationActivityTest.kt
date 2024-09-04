package com.kaspersky.kaspresso.tutoreal

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutoreal.screen.MainSceen
import com.kaspersky.kaspresso.tutoreal.screen.NotificationScreen
import com.kaspersky.kaspresso.tutoreal.screen.ShowNotificationScreen
import com.kaspersky.kaspresso.tutorial.MainActivity
import org.junit.Rule
import org.junit.Test

class NotificationActivityTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkNatification() = run {

        step("Открытие страницы уведомлений"){
            MainSceen{
                notificationActivityButton{
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }

        step("Показать уведомления"){
            ShowNotificationScreen{
                showNotificationButton{
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }

        step("Проверка текста уведомлений"){
            NotificationScreen{
                title{
                    isDisplayed()
                    hasText("Notification Title")
                }

                content{
                    isDisplayed()
                    hasText("Notification Content")
                }
            }
        }

    }
}
