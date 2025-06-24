package com.zekewang.basemvvmandroid.router

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.zekewang.basemvvmandroid.utils.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationRouter @Inject constructor(
    private val logger: Logger
) {
    private var _navController: NavController? = null

    val isInitialized: Boolean
        get() = _navController != null

    fun setNavController(controller: NavController) {
        _navController = controller
    }

    fun navigate(directions: NavDirections) {
        _navController?.navigate(directions)
            ?: logger.w("NavigationRouter", "NavController is not initialized")
    }

    fun navigateTo(@IdRes resId: Int, args: Bundle? = null, options: NavOptions? = null) {
        _navController?.navigate(resId, args, options)
            ?: logger.w("NavigationRouter", "NavController is not initialized")
    }

    fun navigateWithPopUpTo(
        @IdRes target: Int,
        @IdRes popUpTo: Int,
        inclusive: Boolean = false
    ) {
        val options = navOptions {
            popUpTo(popUpTo) {
                this.inclusive = inclusive
            }
        }
        _navController?.navigate(target, null, options)
            ?: logger.w("NavigationRouter", "NavController is not initialized")
    }

    fun popBackStack() {
        _navController?.popBackStack()
            ?: logger.w("NavigationRouter", "NavController is not initialized")
    }

    fun currentDestination(): NavDestination? = _navController?.currentDestination
}

