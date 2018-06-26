package com.msa.augmentedreality

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_ar.*
import org.jetbrains.anko.toast


class ArActivity : AppCompatActivity() {


    private lateinit var arFragment: ArFragment
    private lateinit var andyRenderable: ModelRenderable
    private lateinit var cardRenderable: ViewRenderable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)
        setSupportActionBar(toolbar)

        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment


        ModelRenderable.builder()
                .setSource(this, R.raw.andy)
                .build()
                .thenAccept { renderable -> andyRenderable = renderable }
                .exceptionally { throwable ->
                    Log.e(ArActivity::class.simpleName, "Unable to load Renderable.", throwable)
                    null
                }

        ViewRenderable.builder()
                .setView(this, R.layout.ar_linear)
                .build()
                .thenAccept { renderable ->
                    cardRenderable = renderable
                    val textView = cardRenderable.view.findViewById<TextView>(R.id.text_hello)
                    textView.setOnClickListener { toast("Clicked") }
                    val imageView = cardRenderable.view.findViewById<ImageView>(R.id.image_product)
                    //imageView.loadUrl("https://i.imgur.com/Kpp6mUu.jpg")
                }




        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->

            if (plane.type == Plane.Type.HORIZONTAL_UPWARD_FACING) {

                // Create the Anchor.
                val anchor = hitResult.createAnchor()
                val anchorNode = AnchorNode(anchor)
                anchorNode.setParent(arFragment.arSceneView.scene)

                // Create the transformable andy and add it to the anchor.
                /*val andy = TransformableNode(arFragment.transformationSystem)
                andy.setParent(anchorNode)
                andy.renderable = andyRenderable
                andy.select()*/

                cardRenderable.view.findViewById<TextView>(R.id.text_hello).text = "Time is ${System.currentTimeMillis()}"
                cardRenderable.view.findViewById<ImageView>(R.id.image_product).loadUrl("https://i.imgur.com/Kpp6mUu.jpg")

                val card = TransformableNode(arFragment.transformationSystem)
                card.setParent(anchorNode)
                card.renderable = cardRenderable
                card.select()


                /*val viewRenderable = ViewRenderable.builder()
                        .setView(this, R.layout.ar_linear)
                        .build().getNow(null)

                if (viewRenderable != null) {

                    viewRenderable.view.findViewById<TextView>(R.id.text_hello).text = "Time is ${System.currentTimeMillis()}"
                    viewRenderable.view.findViewById<ImageView>(R.id.image_product).loadUrl("https://i.imgur.com/Kpp6mUu.jpg")


                    val card = TransformableNode(arFragment.transformationSystem)
                    card.setParent(anchorNode)
                    card.renderable = viewRenderable
                    card.select()
                }*/

                /*var viewRenderable2: ViewRenderable? = null

                ViewRenderable.builder()
                        .setView(this, R.layout.ar_linear)
                        .build()
                        .thenAccept { renderable ->
                            viewRenderable2 = renderable
                            val textView = viewRenderable2?.view?.findViewById<TextView>(R.id.text_hello)
                            textView?.setOnClickListener { toast("Clicked") }
                            val imageView = viewRenderable2?.view?.findViewById<ImageView>(R.id.image_product)
                            //imageView.loadUrl("https://i.imgur.com/Kpp6mUu.jpg")
                        }

                viewRenderable2?.apply {
                    val card = TransformableNode(arFragment.transformationSystem)
                    card.setParent(anchorNode)
                    card.renderable = this
                    card.select()
                }*/

            }
        }

    }


    override fun onStart() {
        super.onStart()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
