package com.example.app

import io.flutter.embedding.android.FlutterActivity
import android.os.Bundle
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "audio_helper"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            call, result ->
            if (call.method == "audioOutputAvailable") {
                val arguments = call.arguments as Map<String, Any>
                val type = arguments["type"] as Int
                result.success(AudioHelper(this).audioOutputAvailable(type))
            } else {
                result.notImplemented()
            }
        }
    }
}
