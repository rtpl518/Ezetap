package com.ezetap.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.ezetap.R

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {
    private var _binding: VB? = null
    val binding
        get() = _binding!!

    abstract val viewModel: VM
    abstract fun getBindingInflater(): VB
    abstract fun initUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = getBindingInflater()
        setContentView(binding.root)
        initUI()
    }

    fun setToolbarTitle(title: String?) {
        supportActionBar?.title = title ?: getString(R.string.app_name)
    }
}