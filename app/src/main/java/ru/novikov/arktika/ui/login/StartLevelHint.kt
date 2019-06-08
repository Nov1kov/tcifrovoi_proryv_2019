package ru.novikov.arktika.ui.login

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.novikov.arktika.R
import java.sql.Time
import java.util.*
import kotlin.concurrent.schedule

interface StartMissionCallBackDialog{
    fun start()
    fun back()
}

open class StartLevelHint : DialogFragment(), OnClickListener {

    internal val LOG_TAG = "StartLevelHint"

    var callback: StartMissionCallBackDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        val v = inflater.inflate(R.layout.start_hint_fragment, null)
        val start = v.findViewById<View>(R.id.go_button)
        start.setOnClickListener {
            callback?.start()
            dismiss()
        }
        val back = v.findViewById<View>(R.id.back_button)
        back.setOnClickListener {
            callback?.back()
            dismiss()
        }

        val text = v.findViewById<View>(R.id.text)
        text.visibility = INVISIBLE
        val imageShip = v.findViewById<ImageView>(R.id.image_ship)

        Glide.with(this)
            .asGif()
            .centerCrop()
            .load(ru.novikov.arktika.R.raw.ship)
            .listener(object : RequestListener<GifDrawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return true
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.setLoopCount(1)
                    return false
                }

            })
            .into(imageShip);

        Timer().schedule(5000) {
            activity?.runOnUiThread {
                imageShip.visibility = INVISIBLE
                text.visibility = VISIBLE
                text.startAnimation(AlphaAnimation(0.0f, 1.0f))
            }
        }
        return v
    }

    override fun onClick(v: View) {
        Log.d(LOG_TAG, "Dialog 1: " + (v as Button).text)
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        callback = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(LOG_TAG, "Dialog 1: onDismiss")
        callback = null
    }
}