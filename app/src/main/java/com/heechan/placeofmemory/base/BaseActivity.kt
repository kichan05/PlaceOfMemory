package com.heechan.placeofmemory.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseActivity<D : ViewDataBinding>(val resId : Int) : AppCompatActivity() {
    protected lateinit var binding : D

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, resId)
        binding.lifecycleOwner = this
    }

}