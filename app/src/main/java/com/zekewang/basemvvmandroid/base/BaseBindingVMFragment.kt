package com.zekewang.basemvvmandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.zekewang.basemvvmandroid.R
import com.zekewang.basemvvmandroid.common.Event
import com.zekewang.basemvvmandroid.eventbus.FlowEventBus
import com.zekewang.basemvvmandroid.widget.LoadingDialogFragment

abstract class BaseBindingVMFragment<VB : ViewBinding, VM : BaseViewModel>(
    private val bindingFactory: (LayoutInflater) -> VB
) : BaseFragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = requireNotNull(_binding) { "The property of binding has been destroyed." }

    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingFactory.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // showLoading observe
        FlowEventBus.observe<Event.ShowLoading>(lifecycleScope){
//            logger.d(simpleClassName,"loading msg ${it.message}")
            LoadingDialogFragment.show(childFragmentManager)
        }

        FlowEventBus.observe<Event.HideLoading>(lifecycleScope){
            LoadingDialogFragment.dismiss(childFragmentManager)
        }

        // toast ui collect
        FlowEventBus.observe<Event.ShowToast>(lifecycleScope){ event->
            Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
        }

        // 退出登录
        FlowEventBus.observe<Event.ForceLogout>(lifecycleScope) {
            navigationRouter.navigateWithPopUpTo(
                R.id.loginFragment,
                R.id.mainFragment,
                true
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
