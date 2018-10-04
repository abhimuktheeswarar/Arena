package com.abhi.spectacle.scrollingdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import com.abhi.spectacle.R
import com.abhi.spectacle.epoxy.KotlinEpoxyModel
import com.abhi.spectacle.utilities.loadUrl
import kotlinx.android.synthetic.main.activity_scrolling_detail.*


class ScrollingDetailActivity : AppCompatActivity() {


    private val TAG = ScrollingDetailActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        window.decorView.setOnApplyWindowInsetsListener { v, insets ->
            val height = insets.systemWindowInsetTop // status bar height
            //Log.d(TAG,"1st statusbarHeight = $height")
            toolbar_layout.scrimVisibleHeightTrigger = height + 4
            insets
        }

        collapsingToolbarLayout_image.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val measuredHeight = collapsingToolbarLayout_image.measuredHeight
                val lp = toolbar_layout.layoutParams
                lp.height = measuredHeight
                toolbar_layout.layoutParams = lp
                collapsingToolbarLayout_image.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })


        image_hero.loadUrl("https://i.imgur.com/oHgo3o9.jpg")

        epoxyRecyclerView_detail.buildModelsWith {

            DetailBasicView("1234").id(1).addTo(it)
            DetailBasicView("1234").id(2).addTo(it)
        }


    }
}


data class DetailBasicView(val price: String) : KotlinEpoxyModel(R.layout.item_scrolling_detail) {

    private val priceTextView by bind<TextView>(R.id.text_price)
    private val imageView by bind<ImageView>(R.id.image_hero)

    override fun bind() {

        priceTextView.text = price
        //imageView.loadUrl("https://i.imgur.com/oHgo3o9.jpg")

    }
}