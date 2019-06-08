package ru.novikov.arktika.ui.login

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import ru.novikov.arktika.R


interface LevelCompleteCallBack{
    fun onClickAgain()
    fun onClickGo()
}

class CompleteLevelDialog : DialogFragment(), OnClickListener {

    internal val LOG_TAG = "StartLevelHint"

    var callback: LevelCompleteCallBack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setTitle("Title!")
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        val v = inflater.inflate(R.layout.complete_level_fragment, null)
        val start = v.findViewById<View>(R.id.go_button)
        start.setOnClickListener {
            callback?.onClickGo()
            dismiss()
        }
        val again = v.findViewById<View>(R.id.again_button)
        start.setOnClickListener {
            callback?.onClickAgain()
            dismiss()
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