package com.abhi.koinarc

import android.arch.lifecycle.ViewModel
import android.util.Log

class SimpleViewModel(private val repository: Repository) : ViewModel() {


    init {
        Log.d(this.javaClass.simpleName, "init")
    }


    var count = 0

    fun sayHello() = repository.giveHello()
}