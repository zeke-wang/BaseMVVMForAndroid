package com.zekewang.basemvvmandroid.ui.main.me

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.zekewang.basemvvmandroid.base.BaseBindingVMFragment
import com.zekewang.basemvvmandroid.databinding.FragmentMeBinding
import com.zekewang.basemvvmandroid.ex.safeCollect

class MeFragment :
    BaseBindingVMFragment<FragmentMeBinding, MeViewModel>(FragmentMeBinding::inflate) {

    override val viewModel: MeViewModel by viewModels()

    override fun initView(view: View, savedInstanceState: Bundle?) {

        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
        }

    }

    override fun initData(savedInstanceState: Bundle?) {
        safeCollect(viewModel.userInfoFlow) { userInfo ->
            binding.meTv.text = userInfo.toString()
        }
    }

}