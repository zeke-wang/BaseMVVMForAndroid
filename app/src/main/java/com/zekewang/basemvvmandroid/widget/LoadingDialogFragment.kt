package com.zekewang.basemvvmandroid.widget

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.zekewang.basemvvmandroid.R
import androidx.core.graphics.drawable.toDrawable

class LoadingDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val indicator = CircularProgressIndicator(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
            isIndeterminate = true
        }

        val container = FrameLayout(requireContext()).apply {
            val padding = resources.getDimensionPixelSize(R.dimen.padding_lg)
            setPadding(padding, padding, padding, padding)
            setBackgroundResource(R.drawable.bg_loading_material)
            addView(indicator)
        }

        return Dialog(requireContext()).apply {
            setContentView(container)
            setCancelable(false)
            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        }
    }

    companion object {
        private const val TAG = "LoadingDialog"

        fun show(manager: FragmentManager) {
            if (manager.findFragmentByTag(TAG) == null) {
                LoadingDialogFragment().show(manager, TAG)
            }
        }

        fun dismiss(manager: FragmentManager) {
            (manager.findFragmentByTag(TAG) as? LoadingDialogFragment)
                ?.dismissAllowingStateLoss()
        }
    }
}
