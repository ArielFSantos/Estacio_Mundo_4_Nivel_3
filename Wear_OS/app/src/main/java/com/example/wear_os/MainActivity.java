package com.example.wear_os;

import android.content.Context;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AudioHelper audioHelper;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa o AudioHelper com o contexto atual
        audioHelper = new AudioHelper(this);

        // Inicializa o AudioManager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Verifica se há alto-falante integrado disponível
        boolean isSpeakerAvailable = audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER);

        // Verifica se um fone de ouvido Bluetooth está conectado
        boolean isBluetoothHeadsetConnected = audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP);

        // Registra o callback para detectar a adição ou remoção de dispositivos de áudio
        registerAudioDeviceCallback();
    }

    private void registerAudioDeviceCallback() {
        if (audioManager != null) {
            audioManager.registerAudioDeviceCallback(new AudioDeviceCallback() {
                @Override
                public void onAudioDevicesAdded(AudioDeviceInfo[] addedDevices) {
                    super.onAudioDevicesAdded(addedDevices);

                    if (audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP, addedDevices)) {
                        // Um fone de ouvido Bluetooth acabou de ser conectado
                    }
                }

                @Override
                public void onAudioDevicesRemoved(AudioDeviceInfo[] removedDevices) {
                    super.onAudioDevicesRemoved(removedDevices);

                    if (!audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP, removedDevices)) {
                        // Um fone de ouvido Bluetooth não está mais conectado
                    }
                }
            }, null);
        }
    }

    private boolean audioOutputAvailable(int type, AudioDeviceInfo[] devices) {
        if (devices != null) {
            for (AudioDeviceInfo device : devices) {
                if (device.getType() == type) {
                    return true;
                }
            }
        }
        return false;
    }
}
