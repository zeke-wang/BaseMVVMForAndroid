package com.zekewang.basemvvmandroid.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zekewang.basemvvmandroid.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A generic base fragment class with ViewBinding integration.
 */

@AndroidEntryPoint
abstract class BaseFragment: Fragment() {
    protected val simpleClassName: String = this.javaClass.simpleName

    @Inject
    lateinit var logger: Logger

    abstract fun initView(view: View, savedInstanceState: Bundle?)
    abstract fun initData(savedInstanceState: Bundle?)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData(savedInstanceState)
        initView(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
    }
}
