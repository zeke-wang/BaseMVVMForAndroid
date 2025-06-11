package com.zekewang.basemvvmandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * A generic base fragment class with ViewBinding integration.
 */
abstract class BaseFragment: Fragment() {
    private val simpleClassName: String = this.javaClass.simpleName
    abstract fun initView(view: View, savedInstanceState: Bundle?)
    abstract fun initData(savedInstanceState: Bundle?)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initData(savedInstanceState)
        initView(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
    }
}
