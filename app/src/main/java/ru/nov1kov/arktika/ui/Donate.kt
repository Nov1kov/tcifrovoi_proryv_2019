package ru.nov1kov.arktika.ui

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import ru.nov1kov.arktika.R


open class Donate : DialogFragment() {

    internal val LOG_TAG = "Donate"

    var callback: DialogCallBack? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        val v = inflater.inflate(R.layout.donate_fragment, null)
        val okButton = v.findViewById<View>(R.id.ok_button)
        okButton.setOnClickListener {
            callback?.start()
            dismiss()
        }
        val againButton = v.findViewById<View>(R.id.again_button)
        againButton.setOnClickListener {
            callback?.back()
            dismiss()
        }
        return v
    }

    override fun onDestroy() {
        super.onDestroy()
        callback = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback = null
    }
}