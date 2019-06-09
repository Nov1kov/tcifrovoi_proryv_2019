package ru.nov1kov.arktika.ui

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import ru.nov1kov.arktika.R
import ru.nov1kov.arktika.utils.sound


open class Quiz : DialogFragment() {

    internal val LOG_TAG = "Quiz"

    private lateinit var mp: MediaPlayer
    var callback: DialogCallBack? = null
    private var countWrongAnswer : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow().setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        val v = inflater.inflate(R.layout.quiz_1_fragment, null)

        val answer1 = v.findViewById<View>(R.id.answer_1)
        answer1.setOnClickListener {
            rightAnswer()
        }
        val answer2 = v.findViewById<View>(R.id.answer_2)
        answer2.setOnClickListener {
            wrongAnswer()
        }
        val answer3 = v.findViewById<View>(R.id.answer_3)
        answer3.setOnClickListener {
            wrongAnswer()
        }
        val answer4 = v.findViewById<View>(R.id.answer_4)
        answer4.setOnClickListener {
            wrongAnswer()
        }

        mp = MediaPlayer()

        return v
    }

    private fun rightAnswer() {
        if (context != null){
            mp.sound(context!!.assets.openFd("right_answer.mp3"))
        }
        callback?.start()
        dismiss()
    }

    private fun wrongAnswer() {
        countWrongAnswer += 1
        if (context != null){
            mp.sound(context!!.assets.openFd("wrong_answer.mp3"))
        }
        callback?.back()
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