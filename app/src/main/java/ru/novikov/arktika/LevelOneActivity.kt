package ru.novikov.arktika

import android.content.Intent
import java.util.Random
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_level.*
import ru.novikov.arktika.model.Barrel
import com.bumptech.glide.Glide
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import ru.novikov.arktika.ui.login.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LevelOneActivity : AppCompatActivity() {
    private var height: Int = 0
    private var width: Int = 0

    private var countOfNewBarrels: Int = 2
    private val timeLeftSeconds: Int = 25
    private val gameStepSeconds: Int = 2
    private val priceCount: Int = 10
    private var currentSeconds: Int = 0
    private var currentScore: Int = 0

    private lateinit var timeLeft: Date;
    private val randomizer: Random = Random()

    private val barrells: MutableMap<Barrel, View> = mutableMapOf()

    private lateinit var task: TimerTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_level)

        val displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        height = displayMetrics.heightPixels
        width = displayMetrics.widthPixels

        root_layout.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

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

    private fun completeLevelHint() {
        val dialog = CompleteLevelDialog()
        dialog.maxScore = timeLeftSeconds / gameStepSeconds * countOfNewBarrels * priceCount
        dialog.currentScore = currentScore
        dialog.barrelsCount = currentScore / priceCount
        dialog.callback = object : LevelCompleteCallBack{
            override fun onClickGo() {
                goNextLevel()
            }

            override fun onClickAgain() {
                startGame()
            }

        }
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "CompleteLevelDialog")
    }

    private fun goNextLevel() {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("MAP_ACTIVITY_STAGE_KEY", 2)
        startActivity(intent)
    }

    private fun showLevelHint() {
        val hint = StartLevelHint()
        hint.isCancelable = false
        hint.callback = object : StartMissionCallBackDialog {
            override fun back() {
                finish()
            }

            override fun start() {
                startGame()
            }
        }
        hint.show(supportFragmentManager, "StartLevelHint")
    }

    private fun startGame() {
        currentScore = 0
        currentSeconds = timeLeftSeconds
        task = Timer().scheduleAtFixedRate(0, 1000) {
            runOnUiThread {
                if (!isDestroyed) {
                    currentSeconds -= 1
                    gameStep(currentSeconds)
                    updateTimer()
                }
            }
        }
    }

    private fun gameStep(currentSeconds: Int) {
        if (currentSeconds <= 0){
            task.cancel()
            completeLevelHint()
            return
        }

        if (currentSeconds % gameStepSeconds == 0){
            removeBarrels()
            generateBarrels()
        }
    }

    private fun updateTimer() {
        timer_text.text = currentSeconds.toString()
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
        currentScore += priceCount
        updatePoints()
        removeItem(barrel, view)
    }

    private fun updatePoints() {
        points_text.text = currentScore.toString()
    }
}
