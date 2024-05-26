package com.dosti.scamfolio.ui.view.routing

sealed class NavRoute(val path : String) {
    object Login : NavRoute("login")
    object Home : NavRoute("home")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}