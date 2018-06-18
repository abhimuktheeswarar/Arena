package com.abhi.koinarc

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log

import kotlinx.android.synthetic.main.activity_main_with_fragment.*
import org.koin.android.architecture.ext.viewModel

class MainWithFragmentActivity : AppCompatActivity() {

    // Inject SimpleViewModel
    val viewModel: SimpleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_with_fragment)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action ${viewModel.count}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            viewModel.count += 1
        }

        Log.i(this.localClassName, "Hello from ViewModel = ${viewModel.sayHello()} with count = ${viewModel.count}")
    }

}
