package ru.nov1kov.arktika.ui

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import ru.nov1kov.arktika.R


open class StartGameHint : DialogFragment() {

    internal val LOG_TAG = "StartGameHint"

    var callback: DialogCallBack? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        val v = inflater.inflate(R.layout.start_game_hint_fragment, null)
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
        return v
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