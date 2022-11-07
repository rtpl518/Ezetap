package com.ezetap.views

import androidx.lifecycle.ViewModelProvider
import com.ezetap.base.BaseActivity
import com.ezetap.databinding.ActivityTwoBinding
import com.ezetap.lib_network.data.CustomViewData
import com.ezetap.render.ViewRenderer
import com.ezetap.utils.Constants
import com.ezetap.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityTwo : BaseActivity<ActivityTwoBinding, MainViewModel>() {

    override val viewModel: MainViewModel
        get() = ViewModelProvider(this)[MainViewModel::class.java]

    override fun getBindingInflater() = ActivityTwoBinding.inflate(layoutInflater)

    override fun initUI() {
        renderViews()
    }

    private fun renderViews() {
        intent.getParcelableArrayListExtra<CustomViewData>(Constants.EXTRA_DATA)?.toList()?.let {
            ViewRenderer.renderView(binding.container, it, null)
        }
    }
}