package com.lebartodev.lib_utils.utils

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class AppCoroutineScope(override val coroutineContext: CoroutineContext) : CoroutineScope {
    override fun toString(): String = "AppCoroutineScope(coroutineContext=$coroutineContext)"
}
