package com.abhi.koinarc

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    // Inject SimplePresenter
    val presenter: SimplePresenter by inject()

    // Inject SimpleViewModel
    val viewModel: SimpleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action ${viewModel.count}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            viewModel.count += 1
            startActivity(Intent(this, TabActivity::class.java))
        }




        Log.i(this.localClassName, "Hello from Presenter = ${presenter.sayHello()}")
        Log.i(this.localClassName, "Hello from ViewModel = ${viewModel.sayHello()} with count = ${viewModel.count}")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
