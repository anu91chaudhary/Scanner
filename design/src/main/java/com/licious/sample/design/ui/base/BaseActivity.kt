package com.licious.sample.design.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 *  Base Activity holds all helper methods that are common for all activity view.
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(){
    val binding: VB by lazy {
        getViewBinding()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
    abstract fun getLogTag(): String
    abstract fun getViewBinding(): VB
}