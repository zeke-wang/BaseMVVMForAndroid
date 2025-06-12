package com.zekewang.basemvvmandroid.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.zekewang.basemvvmandroid.R
import com.zekewang.basemvvmandroid.base.BaseBindingVMFragment
import com.zekewang.basemvvmandroid.databinding.FragmentLoginBinding
import com.zekewang.basemvvmandroid.ex.safeCollect

class LoginFragment : BaseBindingVMFragment<FragmentLoginBinding,LoginViewModel>(FragmentLoginBinding::inflate) {
    override val viewModel: LoginViewModel by viewModels()

    override fun initView(view: View, savedInstanceState: Bundle?) {
        binding.tvLogin.setOnClickListener {
            viewModel.login("admin", "123456")
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

        safeCollect(viewModel.loginSuccess) { success ->
            if (success) {
                findNavController().navigate(
                    R.id.mainFragment,
                    null,
                    navOptions {
                        popUpTo(R.id.loginFragment) {
                            inclusive = true
                        }
                    }
                )
            }
        }
    }
}