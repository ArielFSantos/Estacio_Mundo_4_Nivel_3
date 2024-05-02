package com.example.wear_os; // Substitua com o pacote apropriado do seu projeto

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.media.AudioDeviceInfo;

public class MainActivity extends AppCompatActivity {

    private AudioHelper audioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa o AudioHelper com o contexto atual
        audioHelper = new AudioHelper(this);

        // Verifica se há alto-falante integrado disponível
        boolean isSpeakerAvailable = audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER);

        // Verifica se um fone de ouvido Bluetooth está conectado
        boolean isBluetoothHeadsetConnected = audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP);

        // Aqui você pode usar as variáveis isSpeakerAvailable e isBluetoothHeadsetConnected conforme necessário
        // Por exemplo, você pode exibir uma mensagem ou tomar uma ação com base nesses valores
    }
}
