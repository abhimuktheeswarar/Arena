package com.abhi.spectacle.epoxy.bottom

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.abhi.spectacle.R
import com.abhi.spectacle.epoxy.DummyGridItemModel
import kotlinx.android.synthetic.main.activity_sliding.*

class SlidingActivity : AppCompatActivity() {

    private val simpleGridController by lazy { SimpleGridController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sliding)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        generateDummyDataAndSet()

        val gridLayoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        simpleGridController.spanCount = 2
        gridLayoutManager.spanSizeLookup = simpleGridController.spanSizeLookup
        epoxyRecyclerView_bottom.setController(simpleGridController)
    }

    private fun generateDummyDataAndSet() {

        val dummies = arrayListOf<DummyGridItemModel>()

        for (i in 0..20) {


            dummies.add(DummyGridItemModel_().id(i).title("Title$i"))


        }

        carousel_1.setModels(dummies)
        simpleGridController.setDummies(dummies)
        carousel_2.setModels(dummies)

    }

}
