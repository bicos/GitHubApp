package com.ravy.common_ui

import androidx.annotation.MainThread

class Event<out T>(private val content: T) {
    private val consumedScopes by lazy { HashSet<String>() }

    fun isConsumed(scope: String = "") = scope in consumedScopes

    @MainThread
    fun consume(scope: String = ""): T? {
        return content.takeIf { !isConsumed(scope) }?.also { consumedScopes.add(scope) }
    }

    fun peek(): T = content
}