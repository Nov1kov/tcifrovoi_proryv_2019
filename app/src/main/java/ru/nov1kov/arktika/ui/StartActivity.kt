package ru.nov1kov.arktika.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_start.*
import ru.nov1kov.arktika.R
import ru.nov1kov.arktika.utils.hideControls

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)

        container.hideControls()

        start.setOnClickListener {
            showStartGameExplainDialog()
        }

        Glide.with(this)
            .load(ru.nov1kov.arktika.R.drawable.start_screen_background)
            .centerCrop()
            .into(background);
    }

    override fun onResume() {
        super.onResume()
        container.hideControls()
    }

    fun showStartGameExplainDialog(){
        val hint = StartGameHint()
        hint.isCancelable = false
        hint.callback = object : DialogCallBack {
            override fun back() {

            }

            override fun start() {
                startGame()
            }
        }
        hint.show(supportFragmentManager, "StartLevelOneHint")
    }

    private fun startGame() {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("MAP_ACTIVITY_STAGE_KEY", 1)
        startActivity(intent)
    }
}