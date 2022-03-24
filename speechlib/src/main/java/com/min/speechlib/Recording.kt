package com.min.speechlib

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioFormat
import com.github.squti.androidwaverecorder.WaveRecorder
import java.io.File
import java.lang.Exception

class Recording(private val context: Context) {
    private var folderId: String = "${context.filesDir.absolutePath}/speech"
    private var fileId: String = "recorded.wav"

    private val mediaRecorder: WaveRecorder by lazy {
        createMediaRecorder()
    }

    @SuppressLint("MissingPermission")
    private fun createMediaRecorder(): WaveRecorder {
        return WaveRecorder(getFilePath()).apply {
            waveConfig.sampleRate = 44100
            waveConfig.channels = AudioFormat.CHANNEL_IN_STEREO
            waveConfig.audioEncoding = AudioFormat.ENCODING_PCM_8BIT
            noiseSuppressorActive = true
        }
    }

    fun setPathFile(path : String, fileName : String){
        folderId = path
        fileId = fileName
    }

    fun getFilePath(): String {
        val file = File(folderId)
        if (!file.exists()) file.mkdirs()

        val audio = File(file.absolutePath, fileId)
        if (!audio.exists()) audio.createNewFile()

        return audio.absolutePath
    }

     fun startRecording() {
        try {
            mediaRecorder.startRecording()
        }catch (ex : Exception){

        }
    }

    fun stopRecording() {
        try {
            mediaRecorder.stopRecording()
        }catch (ex : Exception){

        }
    }
}