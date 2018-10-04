package com.abhi.spectacle.epoxy

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.TextView
import com.abhi.spectacle.R
import com.abhi.spectacle.utilities.lazyAndroid
import com.airbnb.epoxy.*
import kotlinx.android.synthetic.main.activity_epoxy.*

class EpoxyActivity : AppCompatActivity() {


    private val epoxyDemoController by lazyAndroid { EpoxyDemoController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epoxy)
        epoxyRecyclerView_demo.setController(epoxyDemoController)
        generateDummyDataAndSet()
    }

    private fun generateDummyDataAndSet() {

        val dummies = arrayListOf<Dummy>()

        for (i in 0..20) {


            dummies.add(Dummy(i, "Title$i"))


        }

        epoxyDemoController.setDummyData(dummies)

    }
}

data class Dummy(val id: Int, val title: String, val imageUrl: String? = null)

class DummyLinearItemViewEpoxyHolder : KotlinEpoxyHolder() {


    val titleTextView by bind<TextView>(R.id.text_title_linear)
}

class DummyGridItemViewEpoxyHolder : KotlinEpoxyHolder() {


    val imageView by bind<ImageView>(R.id.imageView_grid)
}

@EpoxyModelClass(layout = R.layout.item_linear)
abstract class DummyLinearItemModel : EpoxyModelWithHolder<DummyLinearItemViewEpoxyHolder>() {

    @EpoxyAttribute
    lateinit var title: String

    override fun bind(holder: DummyLinearItemViewEpoxyHolder) {
        super.bind(holder)
        holder.titleTextView.text = title
    }
}

@EpoxyModelClass(layout = R.layout.item_grid)
abstract class DummyGridItemModel : EpoxyModelWithHolder<DummyGridItemViewEpoxyHolder>() {

    @EpoxyAttribute
    lateinit var title: String

}

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class DummyGridCarousel(context: Context) : Carousel(context) {


    override fun createLayoutManager(): LayoutManager {
        return GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false)
    }


}


class EpoxyDemoController : TypedEpoxyController<List<Dummy>>() {


    fun setDummyData(data: List<Dummy>) {
        setData(data)
    }

    override fun buildModels(data: List<Dummy>) {

        val epoxyModelsForGrid = arrayListOf<DummyGridItemModel>()

        data.forEach { dummy -> epoxyModelsForGrid.add(DummyGridItemModel_().id(dummy.id).title(dummy.title)) }


        CarouselModel_()
                .id("carousel")
                .models(epoxyModelsForGrid)
                .paddingDp(16)
                //.numViewsToShowOnScreen(2f)
                .initialPrefetchItemCount(2)
                .addTo(this)

        data.forEach { dummy ->

            dummyLinearItem {

                id(dummy.id)
                title(dummy.title)
            }
        }

        val linearEpoxyModelsForGrid = arrayListOf<DummyLinearItemModel>()

        data.forEach { dummy -> linearEpoxyModelsForGrid.add(DummyLinearItemModel_().id(dummy.id).title(dummy.title)) }

        DummyGridCarouselModel_()
                .id("dummyCarousel")
                .models(epoxyModelsForGrid)
                .addTo(this)

        DummyGridCarouselModel_()
                .id("dummyLinearCarousel")
                .models(linearEpoxyModelsForGrid)
                .addTo(this)


    }
}