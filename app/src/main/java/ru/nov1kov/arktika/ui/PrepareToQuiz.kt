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


open class PrepareToQuiz : DialogFragment() {

    internal val LOG_TAG = "PrepareToQuiz"

    var callback: DialogCallBack? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        val v = inflater.inflate(R.layout.prepare_to_quiz_fragment, null)
        val ready = v.findViewById<View>(R.id.ready_button)
        ready.setOnClickListener {
            callback?.start()
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