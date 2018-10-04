package com.abhi.spectacle.scrollingdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.abhi.spectacle.R
import kotlinx.android.synthetic.main.activity_empty_scrolling_detail.*

class EmptyScrollingDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty_scrolling_detail)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        epoxyRecyclerView_detail.buildModelsWith {

            DetailBasicView("1234").id(1).addTo(it)
            DetailBasicView("1234").id(2).addTo(it)
        }
    }
}
