package com.abhi.spectacle

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Fade

class SharedElementTransitionChildActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_element_transition_child)

        val fade = Fade()
        fade.excludeTarget(R.id.appBarLayout_child, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = fade
        window.exitTransition = fade
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }
}
