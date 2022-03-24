package com.min.speechlib

import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.*
import com.mozilla.speechlibrary.SpeechResultCallback
import com.mozilla.speechlibrary.SpeechService
import com.mozilla.speechlibrary.SpeechServiceSettings
import com.mozilla.speechlibrary.stt.STTResult


class SpeechManager(private val context: Context) : DefaultLifecycleObserver {
    lateinit var apiAzure :  SpeechAzureApi
    lateinit var builder: SpeechServiceSettings.Builder
    lateinit var mSpeechService: SpeechService
    private lateinit var mVoiceSearchListener: SpeechResultCallback

    private var mKey : String = ""
    private var mRegion : String = ""
    private var mLanguage : String = ""

    var recording : Recording? = null
    var sentenceCurrent : String = ""

    companion object Factory {
        fun create(context: Context) : SpeechManager = SpeechManager(context)
    }

    fun setLifecycle(lifecycle : Lifecycle) : SpeechManager{
        lifecycle.addObserver(this)
        return this
    }

    fun set(key : String) : SpeechManager{
        mKey = key
        return this
    }

    fun setKey(key : String) : SpeechManager{
        mKey = key
        return this
    }

    fun setRegion(region : String) : SpeechManager{
        mRegion = region
        return this
    }
    fun setLanguage(language : String) : SpeechManager{
        mLanguage = language
        return this
    }

    fun setPathFile(path : String, fileName : String) : SpeechManager{
        recording?.setPathFile(path, fileName)
        return this
    }

    fun getFile() : String{
        return recording?.getFilePath() ?: ""
    }

    fun build() : SpeechManager{
        apiAzure = SpeechAzureApi(key = mKey,
            region = mRegion,
            language = mLanguage)
        recording = Recording(context)
        mSpeechService = SpeechService(context)
        builder = SpeechServiceSettings.Builder()
            .withLanguage("en-US")
            .withStoreSamples(false)
            .withStoreTranscriptions(false)
            .withProductTag("product-tag")
            .withUseDeepSpeech(false)
        return this
    }

    fun setVoiceListener(speechListener: SpeechListener){
        mVoiceSearchListener = object : SpeechResultCallback {
            override fun onStartListen() {
                speechListener.onStart()
                recording?.startRecording()
            }

            override fun onMicActivity(fftsum: Double) {
            }

            override fun onDecoding() {
                recording?.stopRecording()
                speechListener.onComplete(apiAzure.getResult(recording?.getFilePath() ?: "", sentenceCurrent))
            }

            override fun onSTTResult(@Nullable result: STTResult?) {
                println(result)
            }

            override fun onNoVoice() {
                sentenceCurrent = ""
                speechListener.onError("onNoVoice")
                recording?.stopRecording()
                mSpeechService.stop()
            }

            override fun onError(
                @SpeechResultCallback.ErrorType errorType: Int,
                @Nullable error: String?
            ) {
                sentenceCurrent = ""
                speechListener.onError("$error")
                recording?.stopRecording()
                mSpeechService.stop()
            }
        }
    }

    fun start(sentence : String){
        sentenceCurrent = sentence
        mSpeechService.start(builder.build(), mVoiceSearchListener)
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        recording?.stopRecording()
        mSpeechService.stop()
    }
}