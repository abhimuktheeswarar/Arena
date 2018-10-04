/*
 * Copyright 2017, Abhi Muktheeswarar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.abhi.spectacle

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.abhi.spectacle.epoxy.EpoxyActivity
import com.abhi.spectacle.epoxy.bottom.BottomSheetEpoxyActivity
import com.abhi.spectacle.epoxy.bottom.SlidingActivity
import com.abhi.spectacle.scrollingdetail.ScrollingDetailActivity
import com.abhi.spectacle.staggergrid.StaggeredGridActivity
import com.abhi.spectacle.swipetodismiss.EmptySwipeToDismissActivity
import com.abhi.spectacle.swipetodismiss.SwipeBackActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            //startActivity(Intent(this, SharedElementTransitionParentActivity::class.java))
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
        }

        acBtn_transitions.setOnClickListener { startActivity<SharedElementTransitionParentActivity>() }
        acBtn_constraint.setOnClickListener { startActivity<ConstraintPlayActivity>() }
        acBtn_stagger.setOnClickListener { startActivity<StaggeredGridActivity>() }
        acBtn_epoxy.setOnClickListener { startActivity<EpoxyActivity>() }
        acBtn_epoxy_bottom.setOnClickListener { startActivity<BottomSheetEpoxyActivity>() }
        acBtn_sliding.setOnClickListener { startActivity<SlidingActivity>() }
        acBtn_scrolling.setOnClickListener { startActivity<ScrollingDetailActivity>() }
        acBtn_swipe.setOnClickListener {

            val dragDismissActivity = Intent(this, EmptySwipeToDismissActivity::class.java)

            DragDismissIntentBuilder(this)
                    .setTheme(DragDismissIntentBuilder.Theme.LIGHT)    // LIGHT (default), DARK, BLACK, DAY_NIGHT
                    .setPrimaryColorResource(R.color.colorPrimary)    // defaults to a semi-transparent black
                    .setToolbarTitle(null)        // defaults to null
                    .setShowToolbar(true)                // defaults to true
                    .setShouldScrollToolbar(false)       // defaults to true
                    .setFullscreenOnTablets(false)      // defaults to false, tablets will have padding on each side
                    .setDragElasticity(DragDismissIntentBuilder.DragElasticity.NORMAL)  // Larger elasticities will make it easier to dismiss.
                    .setDrawUnderStatusBar(false)       // defaults to false. Change to true if you don't want me to handle the content margin for the Activity. Does not apply to the RecyclerView Activities
                    .build(dragDismissActivity)

            // do anything else that you want to set up the Intent
            // dragDismissActivity.putBoolean("test_bool", true);
            //startActivity(dragDismissActivity)

            startActivity<SwipeBackActivity>()
        }

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
