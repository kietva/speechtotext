package com.min.sample

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.min.speechlib.SpeechListener
import com.min.speechlib.SpeechManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text)


        val speechService = SpeechManager
            .create(this)
            .setLifecycle(lifecycle)
            .setKey("")
            .setRegion("")
            .setLanguage("")
            .build()


        speechService.setVoiceListener(object : SpeechListener{
            override fun onStart() {
            }

            override fun onComplete(result: String) {
                textView.text = result
            }

            override fun onError(error: String) {
            }
        })

        findViewById<Button>(R.id.button).setOnClickListener {
            speechService.start("How are you")
        }

        findViewById<Button>(R.id.play).setOnClickListener {
            audioPlayer(speechService.getFile())
        }
    }

    fun audioPlayer(path: String) {
        //set up MediaPlayer
        val mp = MediaPlayer()
        try {
            mp.setDataSource(path)
            mp.prepare()
            mp.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}