package com.kaspersky.kaspresso.tutoreal.screen

import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.screen.UiScreen

object GooglePlayScreen : UiScreen<GooglePlayScreen>() {

    override val packageName: String = "com.android.vending"

    val signInButton = UiButton {withClassName("Sign in")}

}
