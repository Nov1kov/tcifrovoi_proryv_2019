package ru.novikov.arktika.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_start.*
import ru.novikov.arktika.R

class MapActivity : AppCompatActivity() {

    private var stageLevel: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        stageLevel = intent.getIntExtra("MAP_ACTIVITY_STAGE_KEY", 0)

        container.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        start.setOnClickListener {
            if (stageLevel == 1){
                val intent = Intent(this, LevelOneActivity::class.java)
                startActivity(intent)
            }else{

            }
        }

        val drawableBackgroundRes: Int
        if (stageLevel == 1){
            drawableBackgroundRes = ru.novikov.arktika.R.drawable.map_stage_1
        }else{
            drawableBackgroundRes = ru.novikov.arktika.R.drawable.map_stage_2
        }

        Glide.with(this)
            .load(drawableBackgroundRes)
            .centerCrop()
            .into(background);
    }
}