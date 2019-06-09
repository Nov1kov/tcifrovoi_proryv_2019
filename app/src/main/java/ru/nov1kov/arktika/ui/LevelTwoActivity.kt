package ru.nov1kov.arktika.ui

import android.content.Intent
import java.util.Random
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_level.*
import ru.nov1kov.arktika.model.Barrel
import com.bumptech.glide.Glide
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate
import android.media.MediaPlayer
import android.content.res.AssetFileDescriptor
import android.view.animation.*
import ru.nov1kov.arktika.R
import java.io.IOException
import java.lang.Exception


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LevelTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_level)

        root_layout.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        Glide.with(this)
            .load(R.drawable.background_1)
            .centerCrop()
            .into(image_view);

        showLevelHint()
    }

    override fun onResume() {
        super.onResume()
        root_layout.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    private fun showLevelHint() {
        val hint = StartLevelTwoHint()
        hint.isCancelable = false
        hint.callback = object : DialogCallBack {
            override fun back() {
                finish()
            }

            override fun start() {
                showDonateMessage()
            }
        }
        hint.show(supportFragmentManager, "StartLevelOneHint")
    }

    private fun showDonateMessage() {

    }
}
