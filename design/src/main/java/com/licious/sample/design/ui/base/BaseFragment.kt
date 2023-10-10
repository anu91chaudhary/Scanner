package com.licious.sample.design.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 *  Base Fragment holds all helper methods that are common for all fragment view.
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract fun getLogTag(): String
    val binding: VB by lazy {
        getViewBinding()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    abstract fun getViewBinding(): VB
}