package com.abhi.spectacle.swipetodismiss

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.abhi.spectacle.R
import kotlinx.android.synthetic.main.activity_my_swipe_scrolling.*
import org.jetbrains.anko.toast

class MySwipeScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_swipe_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        elasticDragDismissFrameLayout.addListener(
                object : ElasticDragDismissFrameLayout.SystemChromeFader(window) {
                    override fun onDragDismissed() {
                        // if we drag dismiss downward then the default reversal of the enter
                        // transition would slide content upward which looks weird. So reverse it.
                        if (elasticDragDismissFrameLayout.translationY > 0) {
                            //window.returnTransition = TransitionInflater.from(this@MySwipeScrollingActivity).inflateTransition(R.transition.exit_movie_details)
                            toast("something")
                        }
                        finishAfterTransition()
                    }
                })
    }
}
