package client;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
import com.google.gson.Gson;
import clientui.AudioUI;
import models.AudioModel;

public class AudioClient extends Client {

    private final String transferAudio = "Transfer audio to another source";
    private final String checkWeather = "Check the weather forecast";
    private final String increaseVolume = "Increase Volume";
    private final String decreaseVolume = "Decrease Volume";
    private final String connectToTV = "Connect to the television";
    private final String disconnectFromTV = "Disconect from television";
    private static boolean isTurningOn, isTurningOff, isCheckingWeather, isPlayingMusic;
    private static boolean isIncreasingVolume, isDecreasingVolume, isConnecting, isDisconnecting;

   
    public AudioClient() {
        super();
        serviceType = "_audio._udp.local.";
        ui = new AudioUI(this);
        name = "Audio";
    }

    //sends a message to turn-on the TV
    public void powerOn() { //power_on
        String json = new Gson().toJson(new AudioModel(AudioModel.Action.ON));
        String a = sendMessage(json);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);
        System.out.println("Client Received " + json);

        if (audioM.getAction() == AudioModel.Action.ON) {
            isTurningOn = audioM.getValue();
            ui.updateArea(audioM.getMessage());
        }
    }

    //sends a message to turn-off the TV
    public void powerOff() { //power_off
        String json = new Gson().toJson(new AudioModel(AudioModel.Action.OFF));
        String a = sendMessage(json);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);
        System.out.println("Client Received " + json);

        if (audioM.getAction() == AudioModel.Action.OFF) {
            isTurningOff = audioM.getValue();
            ui.updateArea(audioM.getMessage());
        }
    }

    //sends a message to switch to the next TV channel
    public void checkWeather() {
        String json = new Gson().toJson(new AudioModel(AudioModel.Action.CHECK_WEATHER));
        String a = sendMessage(json);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);
        System.out.println("Client Received " + json);

        if (audioM.getAction() == AudioModel.Action.CHECK_WEATHER) {
            isCheckingWeather = audioM.getValue();
            ui.updateArea(audioM.getMessage());
        }
    }

    //sends a message to switch to the previous television channel
    public void playMusic() {
        String json = new Gson().toJson(new AudioModel(AudioModel.Action.PLAY_MUSIC));
        String a = sendMessage(json);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);
        System.out.println("Client Received " + json);

        if (audioM.getAction() == AudioModel.Action.PLAY_MUSIC) {
            isPlayingMusic = audioM.getValue();
            ui.updateArea(audioM.getMessage());
        }
    }

    //sends a message to increase the volume
    public void increaseVolume() {
        String json = new Gson().toJson(new AudioModel(AudioModel.Action.INCREASE_VOLUME));
        String a = sendMessage(json);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);
        System.out.println("Client Received " + json);

        if (audioM.getAction() == AudioModel.Action.INCREASE_VOLUME) {
            isIncreasingVolume = audioM.getValue();
            ui.updateArea(audioM.getMessage());
        };
    }

    //sends a message to decrease the volume
    public void decreaseVolume() {
        String json = new Gson().toJson(new AudioModel(AudioModel.Action.DECREASE_VOLUME));
        String a = sendMessage(json);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);
        System.out.println("Client Received " + json);

        if (audioM.getAction() == AudioModel.Action.DECREASE_VOLUME) {
            isDecreasingVolume = audioM.getValue();
            ui.updateArea(audioM.getMessage());
        };
    }

    //sends a message to connect to an audio source
    public void connectToTV() {
        String json = new Gson().toJson(new AudioModel(AudioModel.Action.CONNECT));
        String a = sendMessage(json);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);
        System.out.println("Client Received " + json);

        if (audioM.getAction() == AudioModel.Action.CONNECT) {
            isConnecting = audioM.getValue();
            ui.updateArea(audioM.getMessage());
        };
    }

    //sends a message to disconnect from an audio source
    public void disconnectFromTV() {
        String json = new Gson().toJson(new AudioModel(AudioModel.Action.DISCONNECT));
        String a = sendMessage(json);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);
        System.out.println("Client Received " + json);

        if (audioM.getAction() == AudioModel.Action.DISCONNECT) {
            isDisconnecting = audioM.getValue();
            ui.updateArea(audioM.getMessage());
        };
    }

    @Override
    public void updatePoll(String msg) {
        if (msg.equals("Audio is not switched on. Press the 'on' button to turn on.")) {

        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new AudioUI(this);
    }
}
