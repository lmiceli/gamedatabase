package com.lmiceli.gamedatabase.ui

import android.os.Bundle
import androidx.annotation.StringRes
import com.lmiceli.gamedatabase.databinding.ActivityMainBinding
import com.lmiceli.gamedatabase.ui.base.BasicActivity
import com.lmiceli.gamedatabase.ui.base.extensions.showInfo
import com.lmiceli.gamedatabase.utils.ErrorMapper
import com.lmiceli.gamedatabase.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BasicActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun showInfo(@StringRes idText: Int) {
        showInfo(getString(idText))
    }

    override fun showInfo(text: String) {
        this.binding.root.showInfo(text)
    }

    override fun showError(@StringRes idText: Int) {
        showError(getString(idText))
    }

    override fun showError(error: Resource.ERROR<*>) {
        if (error.errorCode != null) {
            showError(getString(ErrorMapper.getMessage(error.errorCode)))
        } else {
            showError(error.message)
        }
    }

    override fun showError(text: String?) {
        showInfo("ERROR: $text")
    }

}
