package com.abhi.spectacle.swipetodismiss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhi.spectacle.R
import xyz.klinker.android.drag_dismiss.activity.DragDismissActivity

class SwipeToDismissActivity : DragDismissActivity() {


    override fun onCreateContent(inflater: LayoutInflater, parent: ViewGroup, savedInstanceState: Bundle?): View {
        /* setSupportActionBar(toolbar)
         fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                     .setAction("Action", null).show()
         }*/
        return inflater.inflate(R.layout.activity_swipe_to_dismiss, parent, false)
    }

}
