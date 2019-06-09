package ru.nov1kov.arktika.ui

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
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.nov1kov.arktika.R


interface LevelCompleteCallBack{
    fun onClickAgain()
    fun onClickGo()
}

class CompleteLevelDialog : DialogFragment() {

    internal val LOG_TAG = "StartLevelOneHint"

    var callback: LevelCompleteCallBack? = null
    var maxScore: Int = 1
    var currentScore: Int = 0
    var barrelsCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        val v = inflater.inflate(R.layout.complete_level_fragment, null)
        val start = v.findViewById<View>(R.id.go_button)
        start.setOnClickListener {
            callback?.onClickGo()
            dismiss()
        }
        val again = v.findViewById<View>(R.id.again_button)
        again.setOnClickListener {
            callback?.onClickAgain()
            dismiss()
        }

        val score = v.findViewById<TextView>(R.id.score_text)
        score.text = currentScore.toString()

        val barrels = v.findViewById<TextView>(R.id.barrels_count_text)
        barrels.text = barrelsCount.toString()

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