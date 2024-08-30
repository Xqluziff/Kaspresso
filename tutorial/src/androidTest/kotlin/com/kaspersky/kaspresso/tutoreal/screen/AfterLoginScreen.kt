package com.kaspersky.kaspresso.tutoreal.screen

import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.tutorial.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KTextView


object AfterLoginScreen  : KScreen<AfterLoginScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val title = KEditText {withId(R.id.title)}
}
