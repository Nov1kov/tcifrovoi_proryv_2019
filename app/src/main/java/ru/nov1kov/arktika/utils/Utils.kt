package ru.nov1kov.arktika.utils

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.view.View
import kotlinx.android.synthetic.main.activity_level.*
import java.io.IOException

fun MediaPlayer.sound(afd: AssetFileDescriptor) {
    if (this.isPlaying) {
        this.stop()
    }

    try {
        this.reset()
        this.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        this.prepare()
        this.start()
    } catch (e: IllegalStateException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun View.hideControls() {
    this.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
}