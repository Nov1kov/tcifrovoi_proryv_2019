package ru.nov1kov.arktika.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_level.*
import ru.nov1kov.arktika.R
import ru.nov1kov.arktika.utils.hideControls


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LevelTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_level)

        root_layout.hideControls()

        Glide.with(this)
            .load(R.drawable.background_1)
            .centerCrop()
            .into(image_view);

        showLevelHint()
    }

    override fun onResume() {
        super.onResume()
        root_layout.hideControls()
    }

    private fun showLevelHint() {
        val hint = StartLevelTwoHint()
        hint.isCancelable = false
        hint.callback = object : DialogCallBack {
            override fun back() {
                finish()
            }

            override fun start() {
                showDonateMessage()
            }
        }
        hint.show(supportFragmentManager, "StartLevelTwoHint")
    }

    private fun showDonateMessage() {
        val hint = Donate()
        hint.isCancelable = false
        hint.callback = object : DialogCallBack {
            override fun back() {
                playAgain()
            }

            override fun start() {
                close()
            }
        }
        hint.show(supportFragmentManager, "Donate")
    }

    private fun close() {
        finishAffinity();
    }

    private fun playAgain() {
        val intent = Intent(this, StartActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
