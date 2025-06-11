package com.zekewang.basemvvmandroid.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zekewang.basemvvmandroid.R
import com.zekewang.basemvvmandroid.base.BaseBindingFragment
import com.zekewang.basemvvmandroid.databinding.FragmentLoginBinding
import com.zekewang.basemvvmandroid.ex.observeToast
import com.zekewang.basemvvmandroid.ex.safeCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseBindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView(view: View, savedInstanceState: Bundle?) {
        binding.tvLogin.setOnClickListener {
            viewModel.login("admin", "123456")
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        observeToast(viewModel.toastFlow)

        safeCollect(viewModel.loginSuccess) { success ->
            if (success) {
                findNavController().navigate(R.id.mainFragment)
            }
        }
    }
}