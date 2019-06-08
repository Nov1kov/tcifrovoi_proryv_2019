package ru.novikov.arktika.ui.login

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import ru.novikov.arktika.R

class StartLevelHint : DialogFragment(), OnClickListener {

    internal val LOG_TAG = "StartLevelHint"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setTitle("Title!")
        val v = inflater.inflate(R.layout.start_hint_fragment, null)
/*        v.findViewById(R.id.btnYes).setOnClickListener(this)
        v.findViewById(R.id.btnNo).setOnClickListener(this)
        v.findViewById(R.id.btnMaybe).setOnClickListener(this)*/
        return v
    }

    override fun onClick(v: View) {
        Log.d(LOG_TAG, "Dialog 1: " + (v as Button).text)
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.d(LOG_TAG, "Dialog 1: onDismiss")
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Log.d(LOG_TAG, "Dialog 1: onCancel")
    }
}