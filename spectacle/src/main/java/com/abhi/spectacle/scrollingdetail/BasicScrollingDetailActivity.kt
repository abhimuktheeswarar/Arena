package com.abhi.spectacle.scrollingdetail

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.abhi.spectacle.R

import kotlinx.android.synthetic.main.activity_basic_scrolling_detail.*

class BasicScrollingDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_scrolling_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.primaryText), PorterDuff.Mode.SRC_ATOP)

        epoxyRecyclerView_detail.buildModelsWith {

            DetailBasicView("1234").id(1).addTo(it)
            DetailBasicView("1234").id(2).addTo(it)
        }
    }

}
