package com.zekewang.basemvvmandroid.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.zekewang.basemvvmandroid.widget.LoadingDialogFragment
import kotlinx.coroutines.launch

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

        initData(savedInstanceState)
        initView(view, savedInstanceState)

        // 自动加载 loadingFlow
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loadingFlow.collect { isLoading ->
                        if (isLoading) {
                            LoadingDialogFragment.show(childFragmentManager)
                        } else {
                            LoadingDialogFragment.dismiss(childFragmentManager)
                        }
                    }
                }

                launch {
                    viewModel.toastFlow.collect {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
