package ru.novikov.arktika

import android.app.Dialog
import java.util.Random
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_fullscreen.*
import ru.novikov.arktika.model.Barrel
import com.bumptech.glide.Glide
import ru.novikov.arktika.ui.login.StartLevelHint
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import ru.novikov.arktika.ui.login.DialogClosable


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LevelOneActivity : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        root_layout.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
//        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            //delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    private var height: Int = 0
    private var width: Int = 0

    private var countOfNewBarrels: Int = 2
    private val timeLeftSeconds: Int = 20
    private val gameStepSeconds: Int = 2
    private val priceCount: Int = 10
    private var currentSeconds: Int = 20
    private var currentPoints: Int = 0

    private lateinit var timeLeft: Date;
    private val randomizer: Random = Random()

    private val barrells: MutableMap<Barrel, View> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true

        val displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels

        // Set up the user interaction to manually show or hide the system UI.
        //fullscreen_content.setOnClickListener { toggle() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        dummy_button.setOnTouchListener(mDelayHideTouchListener)

        //val imageViewTarget = GlideDrawableImageViewTarget(image_view)
//        Glide.with(this).load(R.raw).into(imageViewTarget)

        Glide.with(this)
            .load(ru.novikov.arktika.R.drawable.background_1)
            .centerCrop()
            .into(image_view);

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 30)
        timeLeft = calendar.time

        updatePoints()
        showLevelHint()
    }

    private fun showLevelHint() {
        val hint = StartLevelHint()
        hint.isCancelable = false
        hint.callback = object : DialogClosable {
            override fun onDismiss() {
                startGame()
            }
        }
        hint.show(supportFragmentManager, "startLevelHint")
    }

    private fun startGame() {
        currentSeconds = timeLeftSeconds
        Timer().scheduleAtFixedRate(0, 1000) {
            currentSeconds -= 1
            gameStep(currentSeconds)
            updateTimer()
        }
    }

    private fun gameStep(currentSeconds: Int) {
        if (currentSeconds <= 0){
            // lose
            return
        }

        if (currentSeconds % gameStepSeconds == 0){
            runOnUiThread {
                removeBarrels()
                generateBarrels()
            }
        }
    }

    private fun updateTimer() {
        runOnUiThread {
            timer_text.text = currentSeconds.toString()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun generateBarrels(){
        for (i in 1..countOfNewBarrels){
            createBarrel()
        }
    }

    private fun removeBarrels(){
        if (barrells.isEmpty())
            return

        for (i in 1..countOfNewBarrels){
            val removeIndex = randomizer.nextInt(barrells.size)
            val barrel = barrells.keys.elementAt(removeIndex)
            removeItem(barrel)
        }
    }

    private fun removeItem(barrel: Barrel, view: View? = null) {
        if (view == null){
            val findView = barrells[barrel]
            root_layout.removeView(findView)
        }else{
            root_layout.removeView(view)
        }
        barrells.remove(barrel)
    }

    private fun createBarrel() {
        val barrel = Barrel(UUID.randomUUID().toString())

//        val view = createBarrelLottieView(barrel)
        val view = createBarrelImageView(barrel)
        scaleView(view, 0.0f, 1.0f)

        barrells.put(barrel, view)
    }

    private fun createBarrelLottieView(barrel: Barrel) : View{
        val itemHeight = resources.getDimensionPixelSize(R.dimen.barrel_height)
        val itemWidth = resources.getDimensionPixelSize(R.dimen.barrel_width);

        val rndX = randomizer.nextInt(width - itemWidth)
        val rndY = randomizer.nextInt(height - itemHeight)

        val text_view = LottieAnimationView(this)

        // Creating a LinearLayout.LayoutParams object for text view
        val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
            itemWidth, // This will define text view width
            itemHeight // This will define text view height
        )
        params.setMargins(rndX, rndY, 0, 0)
        text_view.layoutParams = params

        text_view.imageAssetsFolder = "images"
        text_view.setAnimation("barrel.json")
        text_view.loop(true)
        text_view.playAnimation();
        val barrelItem = barrel
        text_view.setOnClickListener(View.OnClickListener {
            doneBarrel(it, barrelItem)
        })
        root_layout.addView(text_view)
        return text_view
    }

    private fun createBarrelImageView(barrel: Barrel) : View{
        val itemHeight = resources.getDimensionPixelSize(R.dimen.barrel_height)
        val itemWidth = resources.getDimensionPixelSize(R.dimen.barrel_width);

        val rndX = randomizer.nextInt(width - itemWidth)
        val rndY = randomizer.nextInt(height - itemHeight)

        val imageView = ImageView(this)

        // Creating a LinearLayout.LayoutParams object for text view
        val params: FrameLayout.LayoutParams = FrameLayout.LayoutParams(
            itemWidth, // This will define text view width
            itemHeight // This will define text view height
        )

        Glide.with(this)
            .load(R.drawable.barrel_01)
            .into(imageView);

        params.setMargins(rndX, rndY, 0, 0)

        imageView.layoutParams = params
        imageView.setOnClickListener(View.OnClickListener {
            doneBarrel(it, barrel)
        })
        root_layout.addView(imageView)
        return imageView
    }

    fun scaleView(v: View, startScale: Float, endScale: Float) {
        val anim = ScaleAnimation(
            1f, 1f, // Start and end values for the X axis scaling
            startScale, endScale, // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 1f
        ) // Pivot point of Y scaling
        anim.duration = 1000
        anim.interpolator = BounceInterpolator()
        v.startAnimation(anim)
    }

    private fun doneBarrel(view: View, barrel: Barrel) {
        barrel.done = true
        currentPoints += priceCount
        updatePoints()
        removeItem(barrel, view)
    }

    private fun updatePoints() {
        points_text.text = currentPoints.toString()
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first~
        supportActionBar?.hide()
//        fullscreen_content_controls.visibility = View.GONE
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
//        fullscreen_content.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
