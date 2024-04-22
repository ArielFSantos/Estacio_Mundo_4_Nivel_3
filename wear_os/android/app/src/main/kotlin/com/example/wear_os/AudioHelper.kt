package com.example.app

import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.content.pm.PackageManager

class AudioHelper(private val context: Context) {
    private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    // Função para verificar se um tipo de dispositivo de áudio está disponível
    fun audioOutputAvailable(type: Int): Boolean {
        if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false
        }

        val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
        for (device in devices) {
            if (device.type == type) {
                return true
            }
        }
        return false
    }

    // Registro de callback para detectar a adição ou remoção de dispositivos de áudio
    fun registerAudioDeviceCallback() {
        val audioDeviceCallback = object : AudioManager.AudioDeviceCallback() {
            override fun onAudioDevicesAdded(addedDevices: Array<out AudioDeviceInfo>?) {
                super.onAudioDevicesAdded(addedDevices)
                addedDevices?.forEach { device ->
                    if (device.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
                        // Um fone de ouvido Bluetooth foi conectado
                        // Faça algo aqui, se necessário
                    }
                }
            }

            override fun onAudioDevicesRemoved(removedDevices: Array<out AudioDeviceInfo>?) {
                super.onAudioDevicesRemoved(removedDevices)
                removedDevices?.forEach { device ->
                    if (device.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
                        // Um fone de ouvido Bluetooth foi desconectado
                        // Faça algo aqui, se necessário
                    }
                }
            }
        }

        audioManager.registerAudioDeviceCallback(audioDeviceCallback, null)
    }

    // Você pode chamar esta função em algum lugar para registrar o callback de detecção de dispositivo de áudio
    // Por exemplo, no início da sua Activity ou Fragment
    // registerAudioDeviceCallback()
}

