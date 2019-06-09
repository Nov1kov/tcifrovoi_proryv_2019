package ru.nov1kov.arktika.utils

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
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