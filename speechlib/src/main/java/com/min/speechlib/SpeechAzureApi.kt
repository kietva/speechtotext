package com.min.speechlib

import com.microsoft.cognitiveservices.speech.PhraseListGrammar
import com.microsoft.cognitiveservices.speech.SpeechConfig
import com.microsoft.cognitiveservices.speech.SpeechRecognizer
import com.microsoft.cognitiveservices.speech.audio.AudioConfig
import java.lang.Exception

class SpeechAzureApi(key: String, region: String, language: String) {
    var speechConfig: SpeechConfig

    init {
        synchronized(this) {
            speechConfig = SpeechConfig.fromSubscription(
                key,
                region
            ).apply {
                speechRecognitionLanguage = language
            }
        }
    }

    private fun getSpeechRecognizer(audioConfig: AudioConfig, phrase: String?): SpeechRecognizer {
        synchronized(this) {
            return SpeechRecognizer(speechConfig, audioConfig).also {
                if (!phrase.isNullOrBlank()) {
                    PhraseListGrammar.fromRecognizer(it).addPhrase(phrase)
                }
            }
        }
    }

    fun getResult(path: String, sentence: String): String {
        return try {
            getSpeechRecognizer(AudioConfig.fromWavFileInput(path), sentence).recognizeOnceAsync()
                .get().text
        } catch (ex: Exception) {
            ex.printStackTrace()
            ""
        }
    }
}