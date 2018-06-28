package com.msa.augmentedreality

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val TAG = ArActivity::class.simpleName


    private lateinit var arFragment: ArFragment
    private lateinit var andyRenderable: ModelRenderable
    private lateinit var cardRenderable: ViewRenderable

    var anchorNode: AnchorNode? = null

    val imageUrls = listOf(
            "https://i.imgur.com/Kpp6mUu.jpg",
            "https://i.imgur.com/bUI4GKw.jpg",
            "https://i.imgur.com/oHgo3o9.jpg",
            "https://i.imgur.com/y55lQZA.jpg",
            "https://i.imgur.com/WcdmCEX.jpg",
            "https://i.imgur.com/MA8aPdb.jpg",
            "https://i.imgur.com/gwnsnna.jpg")

    val imageNames = listOf(
            "Ultra casual beach shorts with a linen white shirt",
            "All Light shades of grey top with a light blue bottom",
            "Jungle green round neck shirt paired sky blue jeans & a white sneakers",
            "Stealth look black all dress",
            "Semi casual denim shirt to go with dark blue blazer & a khaki trousers",
            "Almost black winter jacket & a cream white hood",
            "Dark blue hood with white round neck blue jeans")

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
                anchorNode = AnchorNode(anchor)
                anchorNode?.setParent(arFragment.arSceneView.scene)

                // Create the transformable andy and add it to the anchor.
                /*val andy = TransformableNode(arFragment.transformationSystem)
                andy.setParent(anchorNode)
                andy.renderable = andyRenderable
                andy.select()*/

                /*cardRenderable.view.findViewById<TextView>(R.id.text_hello).text = "Time is ${System.currentTimeMillis()}"
                cardRenderable.view.findViewById<ImageView>(R.id.image_product).loadUrl("https://i.imgur.com/Kpp6mUu.jpg")

                val card = TransformableNode(arFragment.transformationSystem)
                card.setParent(anchorNode)
                card.renderable = cardRenderable
                card.select()*/

                /*val cardView: ViewRenderable? = ViewRenderable.builder()
                        .setView(this, R.layout.ar_linear)
                        .build()
                        .get()
                cardView?.apply {

                    this.view.findViewById<TextView>(R.id.text_hello).text = "Time is ${System.currentTimeMillis()}"
                    this.view.findViewById<ImageView>(R.id.image_product).loadUrl("https://i.imgur.com/Kpp6mUu.jpg")

                    val card = TransformableNode(arFragment.transformationSystem)
                    card.setParent(anchorNode)
                    card.renderable = this
                    card.select()

                }*/

                ViewRenderable.builder()
                        .setView(this, R.layout.ar_linear)
                        .build()
                        .thenAcceptAsync {

                            Log.d(TAG, "viewRendered")

                            this.runOnUiThread {

                                Log.d(TAG, "View going to show")

                                val n = (0..6).shuffled().last()

                                it.view.findViewById<TextView>(R.id.text_hello).text = imageNames[n]
                                it.view.findViewById<ImageView>(R.id.image_product).loadUrl(imageUrls[n])

                                val card = TransformableNode(arFragment.transformationSystem)
                                card.setParent(anchorNode)
                                card.renderable = it
                                card.select()
                            }
                        }


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


/**
 * A placeholder fragment containing a simple view.
 */
class ArActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_ar, container, false)
    }
}

