package com.lmiceli.gamedatabase.ui.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lmiceli.gamedatabase.utils.Resource

open class BasicFragment : Fragment() {

    protected fun showError(@StringRes idText: Int) {
        basicActivity()?.showError(idText)
    }

    fun showError(message: String?) {
        basicActivity()?.showError(message)
    }

    protected fun showError(error: Resource.ERROR<*>) {
        basicActivity()?.showError(error)
    }

    fun showInfo(message: String) {
        basicActivity()?.showInfo(message)
    }

    private fun basicActivity() = (requireActivity() as? BasicActivity)

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }

}
