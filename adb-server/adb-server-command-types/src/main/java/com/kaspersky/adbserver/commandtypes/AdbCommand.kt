package com.kaspersky.adbserver.commandtypes

import com.kaspersky.adbserver.common.api.Command

data class AdbCommand(val command: String) : Command(command)
