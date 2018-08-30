package services;

import com.google.gson.Gson;
import models.TelevisionModel;

import serviceui.ServiceUI;

/* @author:David Donovan x14111021
 *
 * @Author skeleton code by Dominic Carr	
 */
public class TelevisionService extends Service {

    private int currentChannel, currentVolume;
    private final int firstChannel, lastChannel, lowestVolume, highestVolume;
    private static boolean isOn, isOff, isIncreasingVolume, isDecreasingVolume;
    private static boolean isSwitchingNext, isSwitchingPrevious, isConnected;
    private static boolean isConnecting, isDisconnecting, connectionStatus;
    private String pass = "pass";
    Gson gson = new Gson();

    public TelevisionService(String name) {
        super(name, "_television._udp.local.");
        currentVolume = 40;
        currentChannel = 1;
        lowestVolume = 0;
        highestVolume = 100;
        firstChannel = 1;
        lastChannel = 99;
        isOn = false;
        isOff = true;
        isConnecting = false;
        isConnected = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " + a);
        TelevisionModel teleM = new Gson().fromJson(a, TelevisionModel.class);

        //get status
        if (teleM.getAction() == TelevisionModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.STATUS, msg));
            sendBack(json);
        } //power on the television
        else if (teleM.getAction() == TelevisionModel.Action.ON) {
            powerOn();
            String msg = (isOn) ? "The television is on\n" : "The television has been switched on\n";
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.ON, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isOn) ? "The Television is on\n" : "Switching on the television...\n";
            ui.updateArea(serviceMessage);
        } //power off the television
        else if (teleM.getAction() == TelevisionModel.Action.OFF) {
            powerOff();
            String msg = (isOff) ? "The television has been switched off\n" : "The telvision has been switched on\n";
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.OFF, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isOff) ? "Switching off the television...\n" : "...";
            ui.updateArea(serviceMessage);
        } //next channel
        else if (teleM.getAction() == TelevisionModel.Action.NEXT_CHANNEL) {
            nextChannel();
            String msg = (isSwitchingNext) ? "Switched to the next channel\n" : "Switched to channel " + currentChannel + "\n";
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.NEXT_CHANNEL, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isSwitchingNext) ? "Current channel: " + currentChannel + "\n" : "Switching to the next channel...\n";
            ui.updateArea(serviceMessage);
        } //previous channel
        else if (teleM.getAction() == TelevisionModel.Action.PREVIOUS_CHANNEL) {
            previousChannel();
            String msg = (isSwitchingPrevious) ? "Switched to the previous channel\n" : "Switched to channel " + currentChannel + "\n";
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.PREVIOUS_CHANNEL, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isSwitchingPrevious) ? "Current channel: " + currentChannel + "\n" : "Switching to the previous channel...\n";
            ui.updateArea(serviceMessage);
        } //increase volume
        else if (teleM.getAction() == TelevisionModel.Action.INCREASE_VOLUME) {
            increaseVolume();
            String msg = (isIncreasingVolume) ? "Volume has been set to" + currentVolume + "\n" : "Volume set to " + currentVolume + "\n";
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.INCREASE_VOLUME, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isIncreasingVolume) ? "Current volume: " + currentVolume + "\n" : "Volume is increasing...\n";
            ui.updateArea(serviceMessage);
        } //decrease volume
        else if (teleM.getAction() == TelevisionModel.Action.DECREASE_VOLUME) {
            decreaseVolume();
            String msg = (isDecreasingVolume) ? "Volume has been set to" + currentVolume + "\n" : "Volume set to " + currentVolume + "\n";
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.DECREASE_VOLUME, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isDecreasingVolume) ? "Current volume: " + currentVolume + "\n" : "Volume is decreasing...\n";
            ui.updateArea(serviceMessage);
        } //connect to speaker
        else if (teleM.getAction() == TelevisionModel.Action.CONNECT) {
            decreaseVolume();
            String msg = (isConnecting) ? "The television connected to speaker: " + connectionStatus + "\n" : "The television connected to speaker: " + connectionStatus + "\n";
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.CONNECT, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isConnecting) ? "The television connected to speaker: " + connectionStatus + "\n" : "The television is connecting to speaker...\n";
            ui.updateArea(serviceMessage);
        } //disconnect from speaker
        else if (teleM.getAction() == TelevisionModel.Action.DISCONNECT) {
            decreaseVolume();
            String msg = (isDisconnecting) ? "The television disconnected from speaker: " + connectionStatus + "\n" : "The television disconnected from speaker: " + connectionStatus + "\n";
            String json = new Gson().toJson(new TelevisionModel(TelevisionModel.Action.DISCONNECT, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isDisconnecting) ? "The television disconnected from speaker: " + connectionStatus + "\n" : "The television is disconnecting from speaker...\n";
            ui.updateArea(serviceMessage);
        } //error
        else {
            sendBack(BAD_COMMAND + " - " + a);
        }

    }

    public void powerOn() { //power on the television
        System.out.println("The television is turned on.\n");
    }

    public void powerOff() { //power on the television
        System.out.println("The television is turned off.\n");
    }

    public void nextChannel() {
        if (currentChannel != lastChannel) {
            currentChannel += 1;
        } else {
            currentChannel = currentChannel;
            ui.updateArea("You are on the last channel.\nUse the 'Channel -' button to switch to the previous channel.\n");
        }
    }

    public void previousChannel() {
        if (currentChannel != firstChannel) {
            currentChannel -= 1;
        } else {
            currentChannel = currentChannel;
            ui.updateArea("You are on the first channel. Use the 'Channel +' button to switch to the next channel.\n.");
        }
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

    public void connectToSpeaker() {
        if (isConnected == false) { //if not connected to the speaker
            //[set audio to speaker]
            ui.updateArea("Connecting to speaker.\n");
        } else { //if already connected to speaker, do nothing
            ui.updateArea("Already connected to speaker.\n");
        }
    }

    public void disconnectFromSpeaker() {
        if (isConnected == true) { //if already connected to the speaker
            //[disconnect from speaker]
            //[set audio to built-in television audio]
            ui.updateArea("Disconnecting from speaker. Audio set to television built-in audio.\n");
        } else { //if already connected to speaker, do nothing
            ui.updateArea("Already disconnected from speaker.\n");
        }
    }
    
    @Override
    public String getStatus() {

        return "Current volume: " + currentVolume + "\nCurrent channel: "
                + currentChannel + "\nConnected to speaker: " + connectionStatus
                + "\n";
    }

    public static void main(String[] args) {
        new TelevisionService("Bedroom");
    }

}
