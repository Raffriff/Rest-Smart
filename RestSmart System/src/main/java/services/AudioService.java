package services;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
import com.google.gson.Gson;
import java.util.Date;
import models.AudioModel;
import serviceui.ServiceUI;

public class AudioService extends Service {

    private int currentVolume;
    private final int lowestVolume, highestVolume;
    private static boolean isOn, isOff, isIncreasingVolume, isDecreasingVolume;
    private static boolean isCheckingWeather, isConnected, isPlaying;
    private static boolean isConnecting, isDisconnecting, connectionStatus;
    private String currentWeather, currentLocation;
    private String pass = "pass";
//    private Date dateUpdated;
    private String dateUpdated;
    private String timeUpdated;
    private String songName, artistName;
    Gson gson = new Gson();

    public AudioService(String name) {
        super(name, "_audio._udp.local.");
        currentVolume = 40;
        lowestVolume = 0;
        highestVolume = 100;
        isOn = false;
        isOff = true;
        isConnecting = false;
        isConnected = false;
        isPlaying = false;
        isCheckingWeather = false;
        currentLocation = "Dublin"; //update
        currentWeather = "rainy"; //update
        dateUpdated = "2018/02/06"; //update
        timeUpdated = "22:10"; //update
        songName = "Barbie Girl";
        artistName = "Aqua";

        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " + a);
        AudioModel audioM = new Gson().fromJson(a, AudioModel.class);

        //get status
        if (audioM.getAction() == AudioModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.STATUS, msg));
            sendBack(json);
        } //power on audio
        else if (audioM.getAction() == AudioModel.Action.ON) {
            powerOn();
            String msg = (isOn) ? "This audio is on\n" : "Audio has been switched on\n";
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.ON, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isOn) ? "The audio is on\n" : "Switching on audio...\n";
            ui.updateArea(serviceMessage);
        } //power off TV
        else if (audioM.getAction() == AudioModel.Action.OFF) {
            powerOff();
            String msg = (isOff) ? "Audio has been switched off\n" : "Audio has been switched on\n";
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.OFF, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isOff) ? "Switching off audio...\n" : "...";
            ui.updateArea(serviceMessage);
        } //check weather
        else if (audioM.getAction() == AudioModel.Action.CHECK_WEATHER) {
            checkWeather();
            String msg = (isCheckingWeather) ? "Current weather in " + currentLocation + ": " + currentWeather + "\n"
                    : "Current weather in " + currentLocation + ": " + currentWeather + "\n";
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.CHECK_WEATHER, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isCheckingWeather) ? "Current weather in " + currentLocation + ": " + currentWeather + "\n"
                    : "Checking the weather in " + currentLocation + "...\n";
            ui.updateArea(serviceMessage);
        } //play music
        else if (audioM.getAction() == AudioModel.Action.PLAY_MUSIC) {
            checkWeather();
            String msg = (isCheckingWeather) ? "Playing '" + songName + "' by '" + artistName + "'\n"
                    : "Playing '" + songName + "' by '" + artistName + "'\n";
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.PLAY_MUSIC, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isCheckingWeather) ? "Playing '" + songName + "' by '" + artistName + "'\n"
                    : "Playing music...\n";
            ui.updateArea(serviceMessage);
        } //increase volume
        else if (audioM.getAction() == AudioModel.Action.INCREASE_VOLUME) {
            increaseVolume();
            String msg = (isIncreasingVolume) ? "Volume has been set to" + currentVolume + "\n" : "Volume set to " + currentVolume + "\n";
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.INCREASE_VOLUME, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isIncreasingVolume) ? "Current volume: " + currentVolume + "\n" : "Volume is increasing...\n";
            ui.updateArea(serviceMessage);
        } //decrease volume
        else if (audioM.getAction() == AudioModel.Action.DECREASE_VOLUME) {
            decreaseVolume();
            String msg = (isDecreasingVolume) ? "Volume has been set to" + currentVolume + "\n" : "Volume set to " + currentVolume + "\n";
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.DECREASE_VOLUME, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isDecreasingVolume) ? "Current volume: " + currentVolume + "\n" : "Volume is decreasing...\n";
            ui.updateArea(serviceMessage);
        } //connect to speaker
        else if (audioM.getAction() == AudioModel.Action.CONNECT) {
            decreaseVolume();
            String msg = (isConnecting) ? "Audio connected to speaker: " + connectionStatus + "\n" : "Audio connected to speaker: " + connectionStatus + "\n";
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.CONNECT, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isConnecting) ? "Audio connected to speaker: " + connectionStatus + "\n" : "Speaker is connecting to TV...\n";
            ui.updateArea(serviceMessage);
        } //disconnect from speaker
        else if (audioM.getAction() == AudioModel.Action.DISCONNECT) {
            decreaseVolume();
            String msg = (isDisconnecting) ? "Audio disconnected from speaker: " + connectionStatus + "\n" : "Audio disconnected from speaker: " + connectionStatus + "\n";
            String json = new Gson().toJson(new AudioModel(AudioModel.Action.DISCONNECT, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isDisconnecting) ? "Audio disconnected from speaker: " + connectionStatus + "\n" : "Audio is disconnecting from Television...\n";
            ui.updateArea(serviceMessage);
        } //error
        else {
            sendBack(BAD_COMMAND + " - " + a);
        }

    }

    public void powerOn() { //powerOnAudio
        System.out.println("Audio is turned on.\n");
    }

    public void powerOff() { //powerOffAudio
        System.out.println("Audio is turned off.\n");
    }

    public void playMusic() {
        System.out.println("Playing music.");
    }

    public void checkWeather() {
        System.out.println("Current weather in " + currentLocation + ": " + currentWeather);
    }

    public void increaseVolume() {
        if (currentVolume != highestVolume) {
            currentVolume += 5;
        } else {
            currentVolume = currentVolume;
            ui.updateArea("You are already on the highest volume.\n");
        }
    }

    public void decreaseVolume() {
        if (currentVolume != lowestVolume) {
            currentVolume -= 5;
        } else {
            currentVolume = currentVolume;
            ui.updateArea("You are already on the lowest volume.\n");
        }
    }

    public void connectToTV() {
        if (isConnected == false) { //if not connected to the speaker
            //[set audio to speaker]
            ui.updateArea("Connecting to the television.\n");
        } else { //if already connected to speaker, do nothing
            ui.updateArea("Already connected to the television.\n");
        }
    }

    public void disconnectFromTV() {
        if (isConnected == true) { //if already connected to the speaker
            //[disconnect from the television]
            ui.updateArea("Disconnecting from the television.\n");
        } else { //if already connected to TV, do nothing
            ui.updateArea("Already disconnected from the television .\n");
        }
    }
    
    @Override
    public String getStatus() {
        return "Current volume: " + currentVolume + "\nCurrent weather in "
                + currentLocation + ": " + currentWeather + "\nLast updated: "
                + dateUpdated + " " + timeUpdated + "\nConnected to TV: " + connectionStatus + "\n";
    }

    public static void main(String[] args) {
        new AudioService("The Bedroom");
    }

}
