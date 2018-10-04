package com.abhi.spectacle.swipetodismiss

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.abhi.spectacle.R
import com.commit451.elasticdragdismisslayout.ElasticDragDismissListener
import kotlinx.android.synthetic.main.activity_scrolling_swipe_to_dismiss.*
import org.jetbrains.anko.toast


class ScrollingSwipeToDismissActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_swipe_to_dismiss)
        setSupportActionBar(toolbar)

        elasticFrameLayout.addListener(object : ElasticDragDismissListener {
            override fun onDrag(elasticOffset: Float, elasticOffsetPixels: Float, rawOffset: Float, rawOffsetPixels: Float) {
                toast("dragging")
            }

            override fun onDragDismissed() {
                //if you are targeting 21+ you might want to finish after transition
                finish()
            }
        })
    }
}
