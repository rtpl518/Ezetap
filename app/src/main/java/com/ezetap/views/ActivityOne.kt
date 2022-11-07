package com.ezetap.views

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ezetap.base.BaseActivity
import com.ezetap.databinding.ActivityOneBinding
import com.ezetap.lib_network.data.CustomViewData
import com.ezetap.render.ViewRenderer
import com.ezetap.utils.Constants
import com.ezetap.utils.gone
import com.ezetap.utils.visible
import com.ezetap.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityOne : BaseActivity<ActivityOneBinding, MainViewModel>() {

    override val viewModel: MainViewModel
        get() = ViewModelProvider(this)[MainViewModel::class.java]

    override fun getBindingInflater() = ActivityOneBinding.inflate(layoutInflater)

    override fun initUI() {
        setupViews()
        observeData()
        fetchCustomViews()
    }

    private fun setupViews() {
        with(binding) {
            layoutError.btnRetry.setOnClickListener { fetchCustomViews() }
        }
    }

    private fun observeData() {
        with(viewModel) {
            customUiLiveData.observe(this@ActivityOne) {
                it?.let {
                    it.views?.let { customViews -> renderViews(it.logoUrl, customViews) }
                    setToolbarTitle(it.headingText)
                }
            }
            errorLiveData.observe(this@ActivityOne) {
                handleError(it.error)
            }
        }
    }

    private fun fetchCustomViews() {
        with(binding) {
            layoutError.root.gone()
            container.gone()
            progressBar.visible()
        }
        viewModel.fetchCustomUI()
    }

    private fun renderViews(logoUrl: String?, customViews: List<CustomViewData>) {
        with(binding) {
            with(container) {
                removeAllViews()
                visible()
            }
            progressBar.gone()
            layoutError.root.gone()
        }
        ViewRenderer.renderView(binding.container, customViews, logoUrl) {
            ViewRenderer.getDataFromAllViews(binding.container)?.let {
                startActivity(Intent(this, ActivityTwo::class.java).apply {
                    putParcelableArrayListExtra(Constants.EXTRA_DATA, it)
                })
            } ?: kotlin.run {
                Toast.makeText(
                    this,
                    "All fields must be filled!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun handleError(error: String?) {
        with(binding) {
            progressBar.gone()
            container.gone()
            with(layoutError) {
                root.visible()
                tvError.text = error
            }
        }
    }
}