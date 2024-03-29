package ru.nov1kov.arktika.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_start.*
import ru.nov1kov.arktika.R
import ru.nov1kov.arktika.utils.hideControls

class MapActivity : AppCompatActivity() {

    private var stageLevel: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        container.hideControls()

        start.setOnClickListener {
            if (stageLevel == 1){
                val intent = Intent(this, LevelOneActivity::class.java)
                startActivity(intent)
            }else if (stageLevel == 2){
                val intent = Intent(this, LevelTwoActivity::class.java)
                startActivity(intent)
            }
        }
        stageLevel = intent.getIntExtra("MAP_ACTIVITY_STAGE_KEY", 0)
        drawBackgorund()
    }

    private fun drawBackgorund() {

        val drawableBackgroundRes: Int
        if (stageLevel == 1) {
            drawableBackgroundRes = R.drawable.map_stage_1
        } else {
            drawableBackgroundRes = R.drawable.map_stage_2
        }

        Glide.with(this)
            .load(drawableBackgroundRes)
            .centerCrop()
            .into(background);
    }

    override fun onResume() {
        super.onResume()
        container.hideControls()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            stageLevel = intent.getIntExtra("MAP_ACTIVITY_STAGE_KEY", 0)
        }
        drawBackgorund()
    }
}