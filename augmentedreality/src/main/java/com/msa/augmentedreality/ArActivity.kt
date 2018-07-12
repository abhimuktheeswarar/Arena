package com.msa.augmentedreality

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_ar.*
import org.jetbrains.anko.toast
import java.io.IOException


class ArActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener, Scene.OnUpdateListener {

    private val TAG = ArActivity::class.simpleName


    private lateinit var arFragment: ArFragment
    private lateinit var andyRenderable: ModelRenderable
    private lateinit var cardRenderable: ViewRenderable

    private val imageUrls = listOf(
            "https://i.imgur.com/Kpp6mUu.jpg",
            "https://i.imgur.com/bUI4GKw.jpg",
            "https://i.imgur.com/oHgo3o9.jpg",
            "https://i.imgur.com/y55lQZA.jpg",
            "https://i.imgur.com/WcdmCEX.jpg",
            "https://i.imgur.com/MA8aPdb.jpg",
            "https://i.imgur.com/gwnsnna.jpg")

    private val imageNames = listOf(
            "Ultra casual beach shorts with a linen white shirt",
            "All Light shades of grey top with a light blue bottom",
            "Jungle green round neck shirt paired sky blue jeans & a white sneakers",
            "Stealth look black all dress",
            "Semi casual denim shirt to go with dark blue blazer & a khaki trousers",
            "Almost black winter jacket & a cream white hood",
            "Dark blue hood with white round neck blue jeans")

    private var isAugmentedImageViewRendered = false

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


    }

    override fun onStart() {
        super.onStart()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        arFragment.setOnTapArPlaneListener(this)
        arFragment.arSceneView.scene.setOnUpdateListener(this)
        //Timer().schedule(5000) { arFragment.planeDiscoveryController.hide() }
        addImageToArImageDb()
    }

    override fun onTapPlane(hitResult: HitResult, plane: Plane, motionEvent: MotionEvent) {


        fun showRenderable() {

            // Create the Anchor.
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            if (anchor.trackingState == TrackingState.TRACKING) {
                //showModelRenderable(anchorNode)
                showViewRenderable(anchorNode)

            }

        }

        if (plane.type == Plane.Type.HORIZONTAL_UPWARD_FACING) {

            showRenderable()

        } else {

            //showRenderable()
        }

    }

    override fun onUpdate(frameTime: FrameTime?) {
        checkForPlanes()
        checkForArImages()
    }


    private fun addImageToArImageDb() {

        val config = arFragment.arSceneView.session.config

        val augmentedImageDatabase = AugmentedImageDatabase(arFragment.arSceneView.session)

        var bitmap: Bitmap? = null
        try {

            assets.open("augmented_images_earth.jpg").use { inputStream -> bitmap = BitmapFactory.decodeStream(inputStream) }

        } catch (e: IOException) {

            Log.e(TAG, "I/O exception loading augmented image bitmap.", e)
            toast("Error adding image to augmentedImageDatabase")
        }

        augmentedImageDatabase.addImage("augmented_images_earth", bitmap)

        config.augmentedImageDatabase = augmentedImageDatabase

        arFragment.arSceneView.session.configure(config)

        Log.d(TAG, "augmentedImageDatabase set")
        toast("augmentedImageDatabase set")

    }


    private fun showModelRenderable(anchorNode: AnchorNode) {

        val andy = TransformableNode(arFragment.transformationSystem)
        andy.setParent(anchorNode)
        andy.renderable = andyRenderable
        andy.select()

    }

    private fun showViewRenderable(anchorNode: AnchorNode) {


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

    private fun checkForPlanes() {

        val planes = arFragment.arSceneView.arFrame.getUpdatedTrackables(Plane::class.java)

        for (plane in planes) {

            if (plane.trackingState == TrackingState.TRACKING) {

                arFragment.planeDiscoveryController.hide()
                //toast("Plane detected")
            }
        }
    }

    private fun checkForArImages() {

        if (!isAugmentedImageViewRendered) {

            val frame = arFragment.arSceneView.arFrame
            val augmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java)


            Log.d(TAG, "augmentedImages size = ${augmentedImages.size}")

            for (augmentedImage in augmentedImages) {

                if (augmentedImage.trackingState == TrackingState.TRACKING) {

                    if (augmentedImage.name == "augmented_images_earth") {

                        val anchor = augmentedImage.createAnchor(augmentedImage.centerPose.extractTranslation())

                        if (anchor.trackingState == TrackingState.TRACKING) {
                            val anchorNode = AnchorNode(anchor)
                            anchorNode.setParent(arFragment.arSceneView.scene)

                            showViewRenderable(anchorNode)

                            isAugmentedImageViewRendered = true

                        } else Log.e(TAG, "anchor tracking state = ${anchor.trackingState}")


                        toast("Image detected")

                    }
                }

            }

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

