package com.msa.augmentedreality

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_ar.*
import kotlinx.android.synthetic.main.content_ar.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.toast
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap


class ArActivity : AppCompatActivity(), BaseArFragment.OnTapArPlaneListener, Scene.OnUpdateListener {

    private val TAG = ArActivity::class.simpleName

    private lateinit var arFragment: ArFragment
    private lateinit var andyRenderable: ModelRenderable
    private lateinit var cardRenderable: ViewRenderable

    private var isAugmentedImageViewRendered = false
    private var isMultiViewRenderingEnabled = false
    private var shouldClearAndPutAnchorsAgain = false

    private var parentAnchor: Anchor? = null
    private var anchorDataMap = LinkedHashMap<Long, AnchorData>()
    private var pendingAnchorNodeToRender: Pair<AnchorNode, Boolean>? = null
    //private val currentAnchors = ArrayList<Anchor>()
    //private var pendingAnchorNodesToRender = ArrayList<AnchorNode>()

    private var isDistanceBasedAnchoring = false

    private val sharedPreferences: SharedPreferences by lazy<SharedPreferences> {

        return@lazy getSharedPreferences("AR", Context.MODE_PRIVATE)

    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        arFragment = supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment


        fab.setOnClickListener { measureDistanceBetweenAnchors() }
        button_anchor_position.setOnClickListener { shouldClearAndPutAnchorsAgain = true }

        switch_ar_anchor_mode.onCheckedChange { switchView, isChecked ->

            isDistanceBasedAnchoring = isChecked

            if (isDistanceBasedAnchoring) {
                switchView?.text = "Distance"
                toast("Distance")
                Log.d(TAG, "Distance")
            } else {
                switchView?.text = "Relative"
                toast("relative")
                Log.d(TAG, "Relative")
            }
        }

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_ar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return when (id) {
            R.id.action_save -> {
                saveAnchorData()
                true
            }
            R.id.action_get -> {
                anchorDataMap = retrieveAnchorData()
                clearAndPutAnchorsAgain()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
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


        if (plane.type == Plane.Type.HORIZONTAL_UPWARD_FACING) {

            showRenderable(hitResult.createAnchor())

        } else {

            //showRenderable(hitResult.createAnchor())
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

    private fun showRenderable(anchor: Anchor) {

        // Create the Anchor.

        val anchorNode = AnchorNode(anchor)
        anchorNode.setParent(arFragment.arSceneView.scene)

        if (anchor.trackingState == TrackingState.TRACKING) {
            //showModelRenderable(anchorNode)
            showViewRenderable(anchorNode)
            if (parentAnchor == null) parentAnchor = anchor
            //else anchorDataMap[System.currentTimeMillis()] = AnchorData(System.currentTimeMillis(), anchor.pose.translation, anchor.pose.rotationQuaternion)
            anchorDataMap[System.currentTimeMillis()] = AnchorData(System.currentTimeMillis(), anchor.pose.translation, anchor.pose.rotationQuaternion, anchorNode.localPosition, anchorNode.localRotation, anchorNode.worldPosition, anchorNode.worldRotation, calculateDistanceFromParentAnchor(parentAnchor!!, anchor))
        }

    }


    private fun showModelRenderable(anchorNode: AnchorNode) {

        //val andy = TransformableNode(arFragment.transformationSystem)
        //andy.setParent(anchorNode)
        //andy.renderable = andyRenderable
        //andy.select()

        ModelRenderable.builder()
                .setSource(this, R.raw.bonsai)
                .build()
                .thenAcceptAsync {

                    Log.d(TAG, "Model going to show")

                    this.runOnUiThread {

                        val model = TransformableNode(arFragment.transformationSystem)
                        model.setParent(anchorNode)
                        model.renderable = it
                        model.select()
                    }
                }

    }

    private fun showViewRenderable(parentNode: AnchorNode) {


        ViewRenderable.builder()
                .setView(this, R.layout.ar_linear)
                .build()
                .thenAcceptAsync {

                    Log.d(TAG, "viewRendered")

                    //it.setPixelsToMetersRatio(DEFAULT_PIXELS_TO_METERS_RATIO)


                    this.runOnUiThread {

                        Log.d(TAG, "View going to show")

                        val n = (0..6).shuffled().last()

                        it.view.findViewById<TextView>(R.id.text_hello).text = imageNames[n]
                        it.view.findViewById<ImageView>(R.id.image_product).loadUrl(imageUrls[n])

                        /*val card = TransformableNode(arFragment.transformationSystem)
                        card.setParent(parentNode)
                        card.renderable = it
                        card.select()*/
                        parentNode.renderable = it
                    }
                }

        if (parentNode.children != null && parentNode.children.size > 0) {

            for (childNode in parentNode.children) {

                ViewRenderable.builder()
                        .setView(this, R.layout.ar_linear)
                        .build()
                        .thenAcceptAsync {

                            Log.d(TAG, "viewRendered")

                            //it.setPixelsToMetersRatio(DEFAULT_PIXELS_TO_METERS_RATIO)

                            this.runOnUiThread {

                                Log.d(TAG, "View going to show")

                                val n = (0..6).shuffled().last()

                                it.view.findViewById<TextView>(R.id.text_hello).text = imageNames[n]
                                it.view.findViewById<ImageView>(R.id.image_product).loadUrl(imageUrls[n])

                                /*val card = TransformableNode(arFragment.transformationSystem)
                                card.setParent(childNode)
                                card.renderable = it
                                card.select()*/

                                childNode.renderable = it

                            }
                        }


            }
        } else Log.e(TAG, "Error showing child nodes ${parentNode.children.size}")


        if (isMultiViewRenderingEnabled) {

            val childNode0 = Node()
            childNode0.setParent(parentNode)
            //childNode0.worldPosition = Vector3(0f, 0.5f, 0f)
            //childNode0.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 45f)
            //childNode0.localRotation = Quaternion(0f, 10f, 0f, 45f)

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
                            card.setParent(childNode0)
                            card.renderable = it
                            card.select()
                        }
                    }

            val childNode1 = Node()
            childNode1.setParent(parentNode)
            //childNode1.worldPosition = Vector3(-1f, 0f, 0f)
            //childNode1.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 45f)
            //childNode1.worldRotation = Quaternion(0f, 10f, 0f, 45f)

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
                            card.setParent(childNode1)
                            card.renderable = it
                            card.select()
                        }
                    }

            val childNode2 = Node()
            childNode2.setParent(parentNode)
            childNode2.worldPosition = Vector3(1f, 0f, 0f)
            childNode2.worldRotation = Quaternion.axisAngle(Vector3(0f, -1f, 0f), 45f)

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
                            card.setParent(childNode2)
                            card.renderable = it
                            card.select()
                        }
                    }
        }

    }

    private fun checkForPlanes() {

        val planes = arFragment.arSceneView.arFrame.getUpdatedTrackables(Plane::class.java)

        for (plane in planes) {

            if (plane.trackingState == TrackingState.TRACKING) {

                arFragment.planeDiscoveryController.hide()
                //toast("Plane detected")
                if (plane.type == Plane.Type.HORIZONTAL_UPWARD_FACING) {

                    if (shouldClearAndPutAnchorsAgain) clearAndPutAnchorsAgain()
                    pendingAnchorNodeToRender?.let {

                        val (anchorNode, shouldRender) = it
                        if (shouldRender) {

                            showViewRenderable(anchorNode)
                            pendingAnchorNodeToRender = Pair(anchorNode, false)
                        }

                    }

                }
            }
        }
    }

    private fun checkForArImages() {

        if (!isAugmentedImageViewRendered) {

            val frame = arFragment.arSceneView.arFrame
            val augmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java)


            //Log.d(TAG, "augmentedImages size = ${augmentedImages.size}")

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

    private fun clearAndPutAnchorsAgain() {

        //parentAnchor?.detach()
        //anchorDataMap.values.forEach { it.detach() }
        pendingAnchorNodeToRender?.first?.anchor?.detach()


        var anchorNode: AnchorNode? = null

        for (plane in arFragment.arSceneView.arFrame.getUpdatedTrackables(Plane::class.java)) {

            Log.d(TAG, "Detected planes = ${plane.type}")

            if (plane.type == Plane.Type.HORIZONTAL_UPWARD_FACING) {
                val parentPose = plane.centerPose.extractTranslation()
                val anchor = arFragment.arSceneView.session.createAnchor(parentPose)
                anchorNode = AnchorNode(anchor)
                anchorNode.setParent(arFragment.arSceneView.scene)
                break
            } else return
        }

        Log.d(TAG, "anchorData size = ${anchorDataMap.size}")

        anchorNode?.apply {

            anchorDataMap.values.drop(1).forEach { anchorData: AnchorData ->

                /*val poseFromAnchorData = Pose(anchorData.translation, anchorData.rotationQuaternion)

                Log.d(TAG, "Pose -> ${poseFromAnchorData.translation.asList()} | rotationQuaternion = ${poseFromAnchorData.rotationQuaternion.asList()}")

                val translationFloatArray = FloatArray(3)
                translationFloatArray[0] = poseFromAnchorData.tx()
                translationFloatArray[1] = poseFromAnchorData.ty()
                translationFloatArray[2] = poseFromAnchorData.tz()

                val rotationQuaternionFloatArray = FloatArray(4)
                rotationQuaternionFloatArray[0] = poseFromAnchorData.qx()
                rotationQuaternionFloatArray[1] = poseFromAnchorData.qy()
                rotationQuaternionFloatArray[2] = poseFromAnchorData.qz()
                rotationQuaternionFloatArray[3] = poseFromAnchorData.qw()*/

                //val pose = Pose(translationFloatArray, rotationQuaternionFloatArray)

                Log.d(TAG, "Adding child...")


                //val childAnchor = arFragment.arSceneView.session.createAnchor(pose)
                //val childAnchorNode = AnchorNode(childAnchor)
                //childAnchorNode.setParent(anchorNode)

                val childNode = Node()
                childNode.setParent(this)

                if (isDistanceBasedAnchoring) {

                    childNode.localPosition = Vector3(0f, 0f, -anchorData.distance)
                    childNode.localRotation = Quaternion.axisAngle(Vector3(0f, 1f, 0f), 0f)


                } else {

                    childNode.localPosition = Vector3(anchorData.localPosition.x, 0f, anchorData.localPosition.z) // Vector3(poseFromAnchorData.ty(), poseFromAnchorData.ty(), poseFromAnchorData.tz())
                    childNode.localRotation = anchorData.localRotation // Quaternion.axisAngle(Vector3(poseFromAnchorData.qx(), poseFromAnchorData.qy(), poseFromAnchorData.qz()), poseFromAnchorData.qw())
                    //childNode.worldPosition = Vector3(anchorData.worldPosition.x, 0f, anchorData.worldPosition.z)
                    //childNode.worldRotation = anchorData.worldRotation
                }

                this.addChild(childNode)
                //if (childAnchor.trackingState == TrackingState.TRACKING) { showViewRenderable(childAnchorNode) }

            }

            Log.d(TAG, "Child count = ${this.children.size}")

            pendingAnchorNodeToRender = Pair(this, true)
            //if (anchorNode.isTracking) showViewRenderable(it)
            shouldClearAndPutAnchorsAgain = false


        } ?: Log.e(TAG, "anchorNode is null ")

    }


    private fun saveAnchorData() {

        if (anchorDataMap.isNotEmpty()) {

            val anchorDataList = ArrayList<AnchorData>()

            anchorDataMap.forEach { id, anchorData -> anchorDataList.add(AnchorData(id, anchorData.translation, anchorData.rotationQuaternion, anchorData.localPosition, anchorData.localRotation, anchorData.worldPosition, anchorData.localRotation, anchorData.distance)) }

            val anchorDataJson = Gson().toJson(anchorDataList)

            val isSaved = sharedPreferences.edit().putString("anchor_data", anchorDataJson).commit()

            toast(if (isSaved) "Saved" else "Not saved")

        }

    }

    private fun retrieveAnchorData(): LinkedHashMap<Long, AnchorData> {

        val anchorDatas = LinkedHashMap<Long, AnchorData>()

        val anchorDataList: List<AnchorData>? = Gson().fromJson(sharedPreferences.getString("anchor_data", null), TypeToken.getParameterized(ArrayList::class.java, AnchorData::class.java).type)

        anchorDataList?.forEach { anchorDatas[it.id] = it }

        return anchorDatas
    }

    private fun measureDistanceBetweenAnchors() {


        if (parentAnchor != null && anchorDataMap.isNotEmpty()) {

            val parentPose = parentAnchor!!.pose
            //To measure distance between camera & anchor
            val cameraPose = arFragment.arSceneView.arFrame.androidSensorPose

            for ((key, anchorData) in anchorDataMap) {

                val childPose = Pose(anchorData.translation, anchorData.rotationQuaternion)

                // Compute the difference vector between the two hit locations.
                val dx = parentPose.tx() - childPose.tx()
                val dy = parentPose.ty() - childPose.ty()
                val dz = parentPose.tz() - childPose.tz()

                // Compute the straight-line distance.
                val distanceMeters = Math.sqrt((dx * dx + dy * dy + dz * dz).toDouble()).toFloat()

                Log.d(TAG, "Distance between parent & child anchor = ${distanceMeters}m")


            }
        } else Log.e(TAG, "anchors not available to measure")
    }

    private fun calculateDistanceFromParentAnchor(parentAnchor: Anchor, childAnchor: Anchor?): Float {

        val dx = parentAnchor.pose.tx() - (childAnchor?.pose?.tx() ?: 0f)
        val dy = parentAnchor.pose.ty() - (childAnchor?.pose?.ty() ?: 0f)
        val dz = parentAnchor.pose.tz() - (childAnchor?.pose?.tz() ?: 0f)

        return Math.sqrt((dx * dx + dy * dy + dz * dz).toDouble()).toFloat()
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

@Serializable
data class AnchorData(val id: Long, val translation: FloatArray, val rotationQuaternion: FloatArray, val localPosition: Vector3, val localRotation: Quaternion, val worldPosition: Vector3, val worldRotation: Quaternion, val distance: Float) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnchorData

        if (id != other.id) return false
        if (!Arrays.equals(translation, other.translation)) return false
        if (!Arrays.equals(rotationQuaternion, other.rotationQuaternion)) return false
        if (localPosition != other.localPosition) return false
        if (localRotation != other.localRotation) return false
        if (worldPosition != other.worldPosition) return false
        if (worldRotation != other.worldRotation) return false
        if (distance != other.distance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + Arrays.hashCode(translation)
        result = 31 * result + Arrays.hashCode(rotationQuaternion)
        result = 31 * result + localPosition.hashCode()
        result = 31 * result + localRotation.hashCode()
        result = 31 * result + worldPosition.hashCode()
        result = 31 * result + worldRotation.hashCode()
        result = 31 * result + distance.hashCode()
        return result
    }


}



