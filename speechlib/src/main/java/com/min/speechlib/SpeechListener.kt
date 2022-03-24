package com.min.speechlib

interface SpeechListener {
    fun onStart()
    fun onComplete(result : String)
    fun onError(error : String)
}