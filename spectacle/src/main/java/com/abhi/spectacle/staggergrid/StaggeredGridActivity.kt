package com.abhi.spectacle.staggergrid

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.abhi.spectacle.R
import com.abhi.spectacle.data.ImgurService
import com.abhi.spectacle.data.poko.ImgurEntities
import com.abhi.spectacle.utilities.ImageLoader
import kotlinx.android.synthetic.main.activity_staggered_grid.*
import kotlinx.android.synthetic.main.content_staggered_grid.*
import kotlinx.android.synthetic.main.item_stagger_grid_0.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.asReference
import java.util.*
import kotlin.collections.ArrayList

class StaggeredGridActivity : AppCompatActivity() {

    private val TAG = StaggeredGridActivity::class.simpleName


    val staggeredLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    val simpleRecyclerViewAdapter = SimpleRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staggered_grid)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        recyclerView_stagger.layoutManager = staggeredLayoutManager
        recyclerView_stagger.adapter = simpleRecyclerViewAdapter
        //setRvItems()
        getImgurImages()
    }

    private fun setRvItems() {

        val simpleItems = ArrayList<SimpleItem>()

        for (i in 0..20) {

            simpleItems.add(SimpleItem(i.toString(), randomStringGenerator(), ""))


        }

        simpleRecyclerViewAdapter.simpleItems = simpleItems

    }

    @Suppress("DeferredResultUnused")
    private fun getImgurImages() {

        val ref = asReference()

        async(UI) {

            try {

                val result = ImgurService.getImgurService(applicationContext).getImages()

                ref.invoke().processData(result.await())

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }

    private fun processData(imgurEntities: ImgurEntities) {
        Log.d(TAG, "Received size = ${imgurEntities.data.size}")

        val simpleItems = ArrayList<SimpleItem>()

        Log.d(TAG, "Data to be added = ${simpleItems.size}")

        for (imgurData in imgurEntities.data) {

            imgurData.images?.let {

                for (image in imgurData.images) {

                    simpleItems.add(SimpleItem(imgurData.id, randomStringGenerator(), image.link))
                    Log.d(TAG, "Image ${imgurData.id} = ${image.id}")

                }

            }


            Log.d(TAG, imgurData.id)


        }

        //println("Data processed")
        Log.d(TAG, "Data processed = ${simpleItems.size}")
        simpleRecyclerViewAdapter.simpleItems = simpleItems

    }

    private fun randomStringGenerator(): String {
        val data = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val random = Random()
        val length = random.nextInt(50) + 1
        val sb = StringBuilder(length)

        for (i in 0 until length) {
            sb.append(data.toCharArray()[random.nextInt(data.length)])
        }

        return sb.toString()
    }
}

data class SimpleItem(val id: String, val title: String, val imageUrl: String)


class StaggeredGridItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    val constraintLayout = itemView.constraintLayout_stagGrid
    val imageView = itemView.imageView
    val titleTextView = itemView.text_title

}


class SimpleRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var simpleItems = ArrayList<SimpleItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
            println("notifyDataSetChanged")
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StaggeredGridItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stagger_grid_0, parent, false))

    }

    override fun getItemCount(): Int {
        return simpleItems.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val simpleItem = simpleItems[position]

        if (holder is StaggeredGridItemHolder) {

            holder.titleTextView.text = simpleItem.id
            holder.imageView.layout(0, 0, 0, 0)
            //holder.imageView.loadUrlMaintaingAspectRatio(simpleItem.imageUrl, holder.constraintLayout)
            //ImageLoader.loadImage(holder.imageView, holder.constraintLayout, simpleItem.imageUrl)
        }
    }


}
