package com.abhi.spectacle.swipetodismiss

import android.os.Bundle
import android.view.*
import com.abhi.spectacle.R
import xyz.klinker.android.drag_dismiss.activity.DragDismissActivity

class EmptySwipeToDismissActivity : DragDismissActivity() {

    override fun onCreateContent(inflater: LayoutInflater, parent: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.activity_empty_swipe_to_dismiss, parent, false)
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