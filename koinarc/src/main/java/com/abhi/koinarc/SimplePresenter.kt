package com.abhi.koinarc

class SimplePresenter(private val repository: Repository) {

    fun sayHello() = repository.giveHello()
}