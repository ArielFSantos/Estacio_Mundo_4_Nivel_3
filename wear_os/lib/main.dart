import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:audio_session/audio_session.dart';
import 'package:just_audio/just_audio.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Doma Wear',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final AudioPlayer _player = AudioPlayer();
  bool _isSpeakerAvailable = false;
  bool _isBluetoothHeadsetConnected = false;

  @override
  void initState() {
    super.initState();
    _checkAudioOutputs();
  }

  Future<void> _checkAudioOutputs() async {
    try {
      bool hasSpeaker = await AudioHelper.audioOutputAvailable(
          AudioDeviceInfo.TYPE_BUILTIN_SPEAKER);
      bool hasBluetoothHeadset = await AudioHelper.audioOutputAvailable(
          AudioDeviceInfo.TYPE_BLUETOOTH_A2DP);
      setState(() {
        _isSpeakerAvailable = hasSpeaker;
        _isBluetoothHeadsetConnected = hasBluetoothHeadset;
      });
    } catch (e) {
      print("Error checking audio outputs: $e");
    }
  }

  Future<void> _playAudio(String audioUrl) async {
    try {
      await _player.setUrl(audioUrl);
      await _player.play();
    } catch (e) {
      print("Error playing audio: $e");
    }
  }

  @override
  void dispose() {
    _player.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Doma Wear'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Audio Outputs:',
            ),
            Text(
              'Speaker: $_isSpeakerAvailable',
            ),
            Text(
              'Bluetooth Headset: $_isBluetoothHeadsetConnected',
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                _playAudio("https://example.com/audio.mp3");
              },
              child: Text('Play Audio'),
            ),
          ],
        ),
      ),
    );
  }
}

class AudioHelper {
  static const MethodChannel _channel = MethodChannel('audio_helper');

  static Future<bool> audioOutputAvailable(int type) async {
    try {
      return await _channel
          .invokeMethod('audioOutputAvailable', {"type": type});
    } catch (e) {
      print("Error invoking method: $e");
      return false;
    }
  }
}
