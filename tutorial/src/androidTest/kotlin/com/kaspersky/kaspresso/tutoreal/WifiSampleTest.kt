package com.kaspersky.kaspresso.tutoreal

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.device.exploit.Exploit
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.tutoreal.screen.MainSceen
import com.kaspersky.kaspresso.tutoreal.screen.WifiScreen
import com.kaspersky.kaspresso.tutorial.MainActivity
import com.kaspersky.kaspresso.tutorial.R
import org.junit.Rule
import org.junit.Test

class WifiSampleTest : TestCase() {
    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test(){

        MainSceen{
            wifiActivityButton{
                isVisible()
                isClickable()
                click()

            }
        }

        WifiScreen{

            device.exploit.setOrientation(Exploit.DeviceOrientation.Portrait)
            checkWifiButton.isVisible()
            checkWifiButton.isClickable()
            wifiStatus.hasEmptyText()
            checkWifiButton.click()
            wifiStatus.hasText(R.string.enabled_status)
            device.network.toggleWiFi(false)
            checkWifiButton.click()
            wifiStatus.hasText(R.string.disabled_status)
            device.exploit.rotate()                         //перевернуть телефон
            wifiStatus.hasText(R.string.disabled_status)


        }




    }
}
