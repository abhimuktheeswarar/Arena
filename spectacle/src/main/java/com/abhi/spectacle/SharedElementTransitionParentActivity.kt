package com.abhi.spectacle

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.transition.Slide
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.util.Pair
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_shared_element_transition_parent.*

class SharedElementTransitionParentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_element_transition_parent)
        setSupportActionBar(toolbar)

        val fade = Fade()
        fade.excludeTarget(R.id.appBarLayout_parent, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = fade
        window.exitTransition = fade


        fab_0.setOnClickListener {

            //View visibility change animation

            TransitionManager.beginDelayedTransition(coordinator_parent, Slide(Gravity.TOP))
            topSlide.visibility = if (topSlide.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE

        }

        fab_1.setOnClickListener { view ->
            val intent = Intent(this, SharedElementTransitionChildActivity::class.java)

            //For single views
            //val options = ActivityOptions.makeSceneTransitionAnimation(this, image_parent, ViewCompat.getTransitionName(image_parent))

            //For multiple views
            val p1: Pair<View, String> = android.util.Pair.create(image_parent, "first_try")
            val p2: Pair<View, String> = android.util.Pair.create(text_parent, "first_text_try")
            val options = ActivityOptions.makeSceneTransitionAnimation(this, p1, p2)
            startActivity(intent, options.toBundle())


        }

    }


}
