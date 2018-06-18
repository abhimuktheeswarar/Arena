package com.abhi.koinarc

interface Repository {

    fun giveHello(): String
}

class MyRepository : Repository {

    override fun giveHello() = "Hello Koin"
}