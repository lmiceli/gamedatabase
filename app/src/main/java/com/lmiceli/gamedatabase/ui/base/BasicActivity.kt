package com.lmiceli.gamedatabase.ui.base

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.lmiceli.gamedatabase.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BasicActivity : AppCompatActivity() {

    abstract fun showInfo(@StringRes idText: Int)
    abstract fun showInfo(text: String)
    abstract fun showError(@StringRes idText: Int)
    abstract fun showError(text: String?)
    abstract fun showError(error: Resource.ERROR<*>)
}
